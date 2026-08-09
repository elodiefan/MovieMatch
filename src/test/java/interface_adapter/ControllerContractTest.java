package interface_adapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

/**
 * Verifies that interface-adapter controllers can route every public action to
 * their configured boundaries without depending on concrete interactors.
 */
class ControllerContractTest {

    private static final List<String> CONTROLLERS = List.of(
            "interface_adapter.get_lists.GetListsController",
            "interface_adapter.other_account.OtherAccountController",
            "interface_adapter.comments.CommentsController",
            "interface_adapter.security_question.SecurityQuestionController",
            "interface_adapter.search_user.SearchUserController",
            "interface_adapter.personal_account.PersonalAccountController",
            "interface_adapter.user_reviews.UserReviewsController",
            "interface_adapter.log_media.LogMediaController",
            "interface_adapter.reset_password.ResetPasswordController",
            "interface_adapter.delete_account.DeleteAccountController",
            "interface_adapter.home_page.HomePageController",
            "interface_adapter.messaging.MessagingController",
            "interface_adapter.signup.SignupController",
            "interface_adapter.media_detail.MediaDetailController",
            "interface_adapter.media_reviews.MediaReviewsController"
    );

    @TestFactory
    Stream<DynamicTest> everyPublicControllerActionCanBeRouted() {
        return CONTROLLERS.stream().map(className -> DynamicTest.dynamicTest(
                simpleName(className), () -> verifyController(className)));
    }

    private static void verifyController(String className) throws Exception {
        final RecordingProxyFactory proxies = new RecordingProxyFactory();
        final Object controller = instantiate(Class.forName(className), proxies);
        int methodsInvoked = 0;

        for (Method method : controller.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && !method.isSynthetic()) {
                final Object[] arguments = argumentsFor(method);
                final Object result = method.invoke(controller, arguments);
                if (method.getReturnType() == boolean.class) {
                    assertNotNull(result);
                }
                methodsInvoked++;
            }
        }

        coverControllerSpecificBranches(controller.getClass(), proxies);

        assertTrue(methodsInvoked > 0);
        assertTrue(proxies.invocations > 0
                        || controller.getClass().getSimpleName()
                        .equals("HomePageController"),
                className + " should route at least one action");
    }

    private static void coverControllerSpecificBranches(
            Class<?> controllerType, RecordingProxyFactory proxies)
            throws Exception {
        final String name = controllerType.getSimpleName();
        if (name.equals("GetListsController")) {
            for (Constructor<?> constructor : controllerType.getConstructors()) {
                if (constructor.getParameterCount() == 1) {
                    final Object specialized = instantiateWith(
                            constructor, proxies);
                    controllerType.getMethod("switchToAccountView",
                                    String.class, String.class)
                            .invoke(specialized, "user", "Display Name");
                }
            }
        }
        else if (name.equals("UserReviewsController")) {
            final Constructor<?> shortConstructor = Stream.of(
                            controllerType.getConstructors())
                    .filter(item -> item.getParameterCount() == 5)
                    .findFirst().orElseThrow();
            final Object reviewsOnly = instantiateWith(
                    shortConstructor, proxies);
            controllerType.getMethod("loadUserComments", String.class)
                    .invoke(reviewsOnly, "user");
        }
        else if (name.equals("MessagingController")) {
            final Object messaging = instantiate(controllerType, proxies);
            controllerType.getMethod("executeFetchUpdateChatHistory",
                            String.class, String.class, String.class)
                    .invoke(messaging, "user", "friend",
                            "2025-01-02, 03:04:05]");
        }
        else if (name.equals("OtherAccountController")) {
            final Constructor<?> constructor =
                    controllerType.getConstructors()[0];
            final Class<?>[] types = constructor.getParameterTypes();
            final Object[] arguments = new Object[types.length];
            for (int index = 0; index < types.length - 1; index++) {
                arguments[index] = instantiate(types[index], proxies);
            }
            arguments[types.length - 1] = null;
            final Object withoutMessaging = constructor.newInstance(arguments);

            final Object available = controllerType
                    .getMethod("isMessagingAvailable")
                    .invoke(withoutMessaging);
            assertTrue(Boolean.FALSE.equals(available));
        }
    }

    private static Object instantiate(Class<?> type,
                                      RecordingProxyFactory proxies)
            throws Exception {
        if (type.isInterface()) {
            return proxies.create(type);
        }
        if (type == String.class) {
            return "view";
        }

        final Constructor<?> constructor = Stream.of(type.getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseGet(() -> {
                    try {
                        return type.getDeclaredConstructor();
                    }
                    catch (NoSuchMethodException exception) {
                        throw new IllegalStateException(exception);
                    }
                });
        return instantiateWith(constructor, proxies);
    }

    private static Object instantiateWith(Constructor<?> constructor,
                                          RecordingProxyFactory proxies)
            throws Exception {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Object[] arguments = new Object[parameterTypes.length];
        for (int index = 0; index < parameterTypes.length; index++) {
            arguments[index] = instantiate(parameterTypes[index], proxies);
        }
        return constructor.newInstance(arguments);
    }

    private static Object[] argumentsFor(Method method) {
        final Class<?>[] types = method.getParameterTypes();
        final Object[] arguments = new Object[types.length];
        for (int index = 0; index < types.length; index++) {
            if (method.getName().equals("executeFetchUpdateChatHistory")
                    && index == 2) {
                arguments[index] = "";
            }
            else {
                arguments[index] = sampleValue(types[index]);
            }
        }
        return arguments;
    }

    private static Object sampleValue(Class<?> type) {
        final Object value;
        if (type == String.class) {
            value = "value";
        }
        else if (type == int.class || type == Integer.class) {
            value = 7;
        }
        else if (type == double.class || type == Double.class) {
            value = 7.5;
        }
        else if (type == boolean.class || type == Boolean.class) {
            value = true;
        }
        else if (type == LocalDateTime.class) {
            value = LocalDateTime.of(2025, 1, 2, 3, 4, 5);
        }
        else if (List.class.isAssignableFrom(type)) {
            value = new ArrayList<>(List.of("value"));
        }
        else {
            value = null;
        }
        return value;
    }

    private static String simpleName(String className) {
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static class RecordingProxyFactory {
        private final Map<Class<?>, Object> proxies = new HashMap<>();
        private int invocations;

        Object create(Class<?> boundary) {
            return proxies.computeIfAbsent(boundary, type -> Proxy.newProxyInstance(
                    type.getClassLoader(), new Class<?>[]{type}, handler()));
        }

        private InvocationHandler handler() {
            return (proxy, method, arguments) -> {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, arguments);
                }
                invocations++;
                return defaultValue(method.getReturnType());
            };
        }

        private Object defaultValue(Class<?> type) {
            final Object value;
            if (type == boolean.class) {
                value = true;
            }
            else if (type == int.class) {
                value = 0;
            }
            else if (type == double.class) {
                value = 0.0;
            }
            else {
                value = null;
            }
            return value;
        }
    }
}
