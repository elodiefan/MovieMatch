package interface_adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/**
 * Shared contract tests for simple interface-adapter state, row and view-model
 * classes. Each production class is reported as a separate JUnit test.
 */
class StateModelContractTest {

    private static final List<String> STATE_CLASSES = List.of(
            "interface_adapter.get_lists.GetListsState",
            "interface_adapter.other_account.OtherAccountState",
            "interface_adapter.comments.CommentsState",
            "interface_adapter.security_question.SecurityQuestionState",
            "interface_adapter.personal_account.PersonalAccountState",
            "interface_adapter.user_reviews.UserReviewsState",
            "interface_adapter.log_media.LogMediaState",
            "interface_adapter.reset_password.ResetPasswordState",
            "interface_adapter.delete_account.DeleteAccountState",
            "interface_adapter.home_page.HomePageState",
            "interface_adapter.messaging.MessagingState",
            "interface_adapter.logged_in.LoggedInState",
            "interface_adapter.signup.SignupState",
            "interface_adapter.media_detail.MediaDetailState",
            "interface_adapter.media_reviews.MediaReviewsState"
    );

    private static final List<String> VIEW_MODEL_CLASSES = List.of(
            "interface_adapter.get_lists.GetListsViewModel",
            "interface_adapter.get_lists.GetBlockedUsersViewModel",
            "interface_adapter.get_lists.GetWatchlistViewModel",
            "interface_adapter.get_lists.GetWatchHistoryViewModel",
            "interface_adapter.other_account.OtherAccountViewModel",
            "interface_adapter.comments.CommentsViewModel",
            "interface_adapter.security_question.SecurityQuestionViewModel",
            "interface_adapter.user_reviews.UserReviewsViewModel",
            "interface_adapter.log_media.LogMediaViewModel",
            "interface_adapter.reset_password.ResetPasswordViewModel",
            "interface_adapter.delete_account.DeleteAccountViewModel",
            "interface_adapter.messaging.MessagingViewModel",
            "interface_adapter.signup.SignupViewModel",
            "interface_adapter.media_detail.MediaDetailViewModel",
            "interface_adapter.media_reviews.MediaReviewsViewModel"
    );

    private static final List<String> ROW_CLASSES = List.of(
            "interface_adapter.get_lists.GetListRow",
            "interface_adapter.comments.CommentRow",
            "interface_adapter.search_user.UserSearchRow",
            "interface_adapter.user_reviews.UserCommentRow",
            "interface_adapter.user_reviews.UserReviewRow",
            "interface_adapter.media_reviews.MediaReviewRow"
    );

    @TestFactory
    Stream<DynamicTest> stateGetterSetterContracts() {
        return STATE_CLASSES.stream().map(className -> DynamicTest.dynamicTest(
                simpleName(className), () -> verifyState(className)));
    }

    @TestFactory
    Stream<DynamicTest> viewModelPropertyChangeContracts() {
        return VIEW_MODEL_CLASSES.stream().map(className ->
                DynamicTest.dynamicTest(simpleName(className),
                        () -> verifyViewModel(className)));
    }

    @TestFactory
    Stream<DynamicTest> rowConstructorAndGetterContracts() {
        return ROW_CLASSES.stream().map(className -> DynamicTest.dynamicTest(
                simpleName(className), () -> verifyRow(className)));
    }

    @Test
    void otherAccountBlockStatusReflectsBlockedFlag() throws Exception {
        final Class<?> type = Class.forName(
                "interface_adapter.other_account.OtherAccountState");
        final Object state = type.getDeclaredConstructor().newInstance();
        final Method setBlocked = type.getMethod("setBlocked", boolean.class);
        final Method getBlockStatus = type.getMethod("getBlockStatus");

        setBlocked.invoke(state, false);
        assertEquals("Block", getBlockStatus.invoke(state));
        setBlocked.invoke(state, true);
        assertEquals("Unblock", getBlockStatus.invoke(state));
    }

    @Test
    void loggedInCopyConstructorCopiesReadableState() throws Exception {
        final Class<?> type = Class.forName(
                "interface_adapter.logged_in.LoggedInState");
        final Object original = type.getDeclaredConstructor().newInstance();
        type.getMethod("setUsername", String.class).invoke(original, "user");
        type.getMethod("setPassword", String.class).invoke(original, "secret");
        type.getMethod("setPasswordError", String.class)
                .invoke(original, "weak password");
        type.getMethod("deleteAccountError", String.class)
                .invoke(original, "cannot delete");

        final Object copy = type.getConstructor(type).newInstance(original);

        assertEquals("user", type.getMethod("getUsername").invoke(copy));
        assertEquals("secret", type.getMethod("getPassword").invoke(copy));
        assertEquals("cannot delete",
                type.getMethod("getDeleteAccountError").invoke(copy));
    }

    private static void verifyState(String className) throws Exception {
        final Object state = Class.forName(className)
                .getDeclaredConstructor().newInstance();
        int settersChecked = 0;

        for (Method setter : state.getClass().getMethods()) {
            if (setter.getName().startsWith("set")
                    && setter.getParameterCount() == 1) {
                final Object value = sampleValue(setter.getParameterTypes()[0]);
                setter.invoke(state, value);
                final String property = setter.getName().substring(3);
                final Method getter = findGetter(state.getClass(), property);
                if (getter != null) {
                    final Object actual = getter.invoke(state);
                    assertEquals(value, actual,
                            setter.getName() + " must update its matching getter");
                    settersChecked++;
                }
            }
        }

        assertTrue(settersChecked > 0, className + " should expose state setters");
        invokeAllGetters(state);
    }

    private static void verifyViewModel(String className) throws Exception {
        final StateModel<?> viewModel = (StateModel<?>) Class.forName(className)
                .getDeclaredConstructor().newInstance();
        assertNotNull(viewModel.getState());
        assertNotNull(viewModel.getViewName());
        assertFalse(viewModel.getViewName().isEmpty());

        final AtomicReference<PropertyChangeEvent> event =
                new AtomicReference<>();
        viewModel.addPropertyChangeListener(event::set);
        viewModel.firePropertyChanged();

        assertNotNull(event.get());
        assertEquals("state", event.get().getPropertyName());
        assertNotSame(event, event.get().getNewValue());
        assertEquals(viewModel.getState(), event.get().getNewValue());
        invokeAllGetters(viewModel);
    }

    private static void verifyRow(String className) throws Exception {
        final Class<?> rowClass = Class.forName(className);
        final Constructor<?> constructor = rowClass.getConstructors()[0];
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final Object[] arguments = new Object[parameterTypes.length];
        for (int index = 0; index < parameterTypes.length; index++) {
            arguments[index] = sampleValue(parameterTypes[index]);
        }

        final Object row = constructor.newInstance(arguments);
        invokeAllGetters(row);

        for (Method method : rowClass.getMethods()) {
            if (method.getName().equals("isLikedBy")) {
                assertTrue((Boolean) method.invoke(row, "user"));
                assertFalse((Boolean) method.invoke(row, new Object[]{null}));
            }
        }
    }

    private static Method findGetter(Class<?> type, String property) {
        Method result = null;
        try {
            result = type.getMethod("get" + property);
        }
        catch (NoSuchMethodException exception) {
            try {
                result = type.getMethod("is" + property);
            }
            catch (NoSuchMethodException ignored) {
                // Some legacy states expose write-only flags.
            }
        }
        return result;
    }

    private static void invokeAllGetters(Object object) throws Exception {
        for (Method method : object.getClass().getMethods()) {
            final boolean getter = method.getParameterCount() == 0
                    && Modifier.isPublic(method.getModifiers())
                    && (method.getName().startsWith("get")
                    || method.getName().startsWith("is"))
                    && !method.getName().equals("getClass");
            if (getter) {
                method.invoke(object);
            }
        }
    }

    private static Object sampleValue(Class<?> type) {
        final Object result;
        if (type == String.class) {
            result = "value";
        }
        else if (type == int.class || type == Integer.class) {
            result = 7;
        }
        else if (type == double.class || type == Double.class) {
            result = 7.5;
        }
        else if (type == boolean.class || type == Boolean.class) {
            result = true;
        }
        else if (List.class.isAssignableFrom(type)) {
            result = new ArrayList<>(List.of("value"));
        }
        else if (Set.class.isAssignableFrom(type)) {
            result = new HashSet<>(Set.of("user"));
        }
        else if (type == ZonedDateTime.class) {
            result = ZonedDateTime.of(2025, 1, 2, 3, 4, 5, 0,
                    ZoneOffset.UTC);
        }
        else {
            result = null;
        }
        return result;
    }

    private static String simpleName(String className) {
        return className.substring(className.lastIndexOf('.') + 1);
    }
}
