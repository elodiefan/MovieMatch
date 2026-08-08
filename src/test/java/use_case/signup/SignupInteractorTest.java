package use_case.signup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import entity.StandardUserFactory;

class SignupInteractorTest {

    private static final class FakeDataAccess implements SignupUserDataAccessInterface {
        private boolean userExists;
        private String savedUsername;
        private String savedDisplayName;
        private String savedPassword;
        private String savedQuestion;
        private String savedAnswer;

        @Override
        public boolean existsByUsername(String username) {
            return userExists;
        }

        @Override
        public void saveUser(String username, String displayName, String password,
                             String securityQuestion, String securityAnswer) {
            savedUsername = username;
            savedDisplayName = displayName;
            savedPassword = password;
            savedQuestion = securityQuestion;
            savedAnswer = securityAnswer;
        }
    }

    private static final class RecordingPresenter implements SignupOutputBoundary {
        private SignupOutputData success;
        private String failure;
        private boolean switchedToLogin;

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failure = errorMessage;
        }

        @Override
        public void switchToLoginView() {
            switchedToLogin = true;
        }
    }

    private static SignupInputData validInput() {
        return new SignupInputData("yidan", "Yidan", "password1", "password1",
                "First pet?", "Mochi");
    }

    @Test
    void validSignupTrimsTextSavesUserAndSucceeds() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        final RecordingPresenter presenter = new RecordingPresenter();
        final SignupInteractor interactor = new SignupInteractor(dataAccess, presenter,
                new StandardUserFactory());

        interactor.execute(new SignupInputData("  yidan  ", "  Yidan Xu  ", "password1",
                "password1", "  First pet?  ", "  Mochi  "));

        assertNull(presenter.failure);
        assertEquals("yidan", presenter.success.getUsername());
        assertEquals("Yidan Xu", presenter.success.getDisplayName());
        assertEquals("yidan", dataAccess.savedUsername);
        assertEquals("Yidan Xu", dataAccess.savedDisplayName);
        assertEquals("password1", dataAccess.savedPassword);
        assertEquals("First pet?", dataAccess.savedQuestion);
        assertEquals("Mochi", dataAccess.savedAnswer);
    }

    @Test
    void duplicateUsernameFailsWithoutSaving() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        dataAccess.userExists = true;
        final RecordingPresenter presenter = new RecordingPresenter();

        new SignupInteractor(dataAccess, presenter, new StandardUserFactory()).execute(validInput());

        assertEquals("Username already exists.", presenter.failure);
        assertNull(presenter.success);
        assertNull(dataAccess.savedUsername);
    }

    @Test
    void invalidFieldsReportTheFirstValidationFailure() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        final RecordingPresenter presenter = new RecordingPresenter();
        final SignupInteractor interactor = new SignupInteractor(dataAccess, presenter,
                new StandardUserFactory());

        interactor.execute(new SignupInputData("ab", "Yidan", "password1", "password1",
                "First pet?", "Mochi"));
        assertTrue(presenter.failure.startsWith("Username must be"));

        final RecordingPresenter passwordPresenter = new RecordingPresenter();
        new SignupInteractor(dataAccess, passwordPresenter, new StandardUserFactory()).execute(
                new SignupInputData("yidan", "Yidan", "password1", "different1",
                        "First pet?", "Mochi"));
        assertEquals("Passwords don't match.", passwordPresenter.failure);
        assertNull(dataAccess.savedUsername);
    }

    @Test
    void switchToLoginDelegatesToPresenter() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new SignupInteractor(new FakeDataAccess(), presenter, new StandardUserFactory())
                .switchToLoginView();

        assertTrue(presenter.switchedToLogin);
        assertNull(presenter.success);
    }
}
