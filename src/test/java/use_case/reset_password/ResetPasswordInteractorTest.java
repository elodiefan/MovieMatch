package use_case.reset_password;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ResetPasswordInteractorTest {

    private static final class FakeDataAccess implements ResetPasswordUserDataAccessInterface {
        private boolean exists = true;
        private String changedUsername;
        private String changedPassword;

        @Override
        public boolean existsByName(String username) {
            return exists;
        }

        @Override
        public void changePassword(String username, String newPassword) {
            changedUsername = username;
            changedPassword = newPassword;
        }
    }

    private static final class RecordingPresenter implements ResetPasswordOutputBoundary {
        private ResetPasswordOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(ResetPasswordOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failure = errorMessage;
        }
    }

    @Test
    void missingAccountFailsWithoutWriting() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        dataAccess.exists = false;
        final RecordingPresenter presenter = new RecordingPresenter();

        new ResetPasswordInteractor(dataAccess, presenter).changePassword(
                new ResetPasswordInputData("missing", "newpass", "newpass"));

        assertEquals("No account found with that username.", presenter.failure);
        assertNull(dataAccess.changedPassword);
    }

    @Test
    void emptyShortAndMismatchedPasswordsFailWithoutWriting() {
        assertFailure("", "", "Password cannot be empty.");
        assertFailure("abc", "abc", "Password must be at least 4 characters.");
        assertFailure("newpass", "different", "The two passwords do not match.");
    }

    @Test
    void validPasswordIsPersistedAndPresented() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        final RecordingPresenter presenter = new RecordingPresenter();

        new ResetPasswordInteractor(dataAccess, presenter).changePassword(
                new ResetPasswordInputData("yidan", "newpass1", "newpass1"));

        assertNull(presenter.failure);
        assertEquals("yidan", dataAccess.changedUsername);
        assertEquals("newpass1", dataAccess.changedPassword);
        assertEquals("yidan", presenter.success.getUsername());
    }

    private static void assertFailure(String password, String confirmation, String expectedMessage) {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        final RecordingPresenter presenter = new RecordingPresenter();

        new ResetPasswordInteractor(dataAccess, presenter).changePassword(
                new ResetPasswordInputData("yidan", password, confirmation));

        assertEquals(expectedMessage, presenter.failure);
        assertNull(dataAccess.changedPassword);
        assertNull(presenter.success);
    }
}
