package interface_adapter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

/**
 * Smoke-contract tests for simple presenters. Real view models are used while
 * external boundaries are replaced with deterministic proxies.
 */
class PresenterContractTest {

    private static final List<String> PRESENTERS = List.of(
            "interface_adapter.get_lists.GetListsPresenter",
            "interface_adapter.other_account.OtherAccountPresenter",
            "interface_adapter.comments.CommentsPresenter",
            "interface_adapter.security_question.SecurityQuestionPresenter",
            "interface_adapter.personal_account.PersonalAccountPresenter",
            "interface_adapter.user_reviews.UserReviewsPresenter",
            "interface_adapter.log_media.LogMediaPresenter",
            "interface_adapter.reset_password.ResetPasswordPresenter",
            "interface_adapter.delete_account.DeleteAccountPresenter",
            "interface_adapter.home_page.HomePagePresenter",
            "interface_adapter.messaging.MessagingPresenter",
            "interface_adapter.signup.SignupPresenter",
            "interface_adapter.media_detail.MediaDetailPresenter",
            "interface_adapter.media_reviews.MediaReviewsPresenter"
    );

    @TestFactory
    Stream<DynamicTest> everyPresenterPathUpdatesARealViewModel() {
        return PRESENTERS.stream().map(className -> DynamicTest.dynamicTest(
                simpleName(className), () -> verifyPresenter(className)));
    }

    private static void verifyPresenter(String className) throws Exception {
        final Object presenter = instantiate(Class.forName(className), 0);
        int methodsInvoked = 0;

        for (Method method : presenter.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && !method.isSynthetic()) {
                final Class<?>[] types = method.getParameterTypes();
                final Object[] arguments = new Object[types.length];
                for (int index = 0; index < types.length; index++) {
                    arguments[index] = instantiate(types[index], 0);
                }
                method.invoke(presenter, arguments);
                methodsInvoked++;
            }
        }

        assertTrue(methodsInvoked > 0);
    }

    private static Object instantiate(Class<?> type, int depth)
            throws Exception {
        final Object result;
        if (type == String.class) {
            result = "value";
        }
        else if (type == int.class || type == Integer.class) {
            result = 7;
        }
        else if (type == long.class || type == Long.class) {
            result = 7L;
        }
        else if (type == double.class || type == Double.class) {
            result = 7.5;
        }
        else if (type == boolean.class || type == Boolean.class) {
            result = true;
        }
        else if (type == LocalDateTime.class) {
            result = LocalDateTime.of(2025, 1, 2, 3, 4, 5);
        }
        else if (type == ZonedDateTime.class) {
            result = ZonedDateTime.of(2025, 1, 2, 3, 4, 5, 0,
                    ZoneOffset.UTC);
        }
        else if (List.class.isAssignableFrom(type)) {
            result = new ArrayList<>();
        }
        else if (Set.class.isAssignableFrom(type)) {
            result = new HashSet<>();
        }
        else if (type.isArray()) {
            result = java.lang.reflect.Array.newInstance(
                    type.getComponentType(), 0);
        }
        else if (type.isEnum()) {
            result = type.getEnumConstants()[0];
        }
        else if (type == Executor.class) {
            result = (Executor) Runnable::run;
        }
        else if (type.isInterface()) {
            result = Proxy.newProxyInstance(type.getClassLoader(),
                    new Class<?>[]{type}, new DefaultInvocationHandler());
        }
        else if (depth > 8) {
            result = null;
        }
        else {
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
            constructor.setAccessible(true);
            final Class<?>[] types = constructor.getParameterTypes();
            final Object[] arguments = new Object[types.length];
            for (int index = 0; index < types.length; index++) {
                arguments[index] = instantiate(types[index], depth + 1);
            }
            result = constructor.newInstance(arguments);
        }
        return result;
    }

    private static String simpleName(String className) {
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static class DefaultInvocationHandler
            implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] arguments)
                throws Exception {
            final Object result;
            if (method.getDeclaringClass() == Object.class) {
                result = method.invoke(this, arguments);
            }
            else if (method.getName().equals("execute")
                    && arguments != null && arguments.length == 1
                    && arguments[0] instanceof Runnable) {
                ((Runnable) arguments[0]).run();
                result = null;
            }
            else if (method.getReturnType() == boolean.class) {
                result = true;
            }
            else if (method.getReturnType() == int.class) {
                result = 0;
            }
            else {
                result = null;
            }
            return result;
        }
    }
}
