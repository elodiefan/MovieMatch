package use_case.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import entity.StandardUser;
import entity.User;

class LoginInteractorTest {

    private static final class FakeDataAccess implements LoginUserDataAccessInterface {
        private User user;
        private User savedUser;
        private String currentUsername;

        @Override
        public boolean existsByUsername(String username) {
            return user != null && user.getUsername().equals(username);
        }

        @Override
        public void save(User userToSave) {
            savedUser = userToSave;
        }

        @Override
        public User get(String username) {
            return user;
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public void setCurrentUsername(String username) {
            currentUsername = username;
        }
    }

    private static final class RecordingPresenter implements LoginOutputBoundary {
        private LoginOutputData success;
        private String failure;
        private boolean switchedToSignup;
        private boolean switchedToHome;

        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failure = errorMessage;
        }

        @Override
        public void switchToSignUpView() {
            switchedToSignup = true;
        }

        @Override
        public void switchToHomePageView() {
            switchedToHome = true;
        }
    }

    @Test
    void unknownAccountFails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new LoginInteractor(new FakeDataAccess(), presenter)
                .execute(new LoginInputData("missing", "password1"));

        assertEquals("missing: Account does not exist.", presenter.failure);
        assertNull(presenter.success);
    }

    @Test
    void incorrectPasswordFailsWithoutChangingSession() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        dataAccess.user = new StandardUser("yidan", "Yidan", "password1", "Question?", "answer");
        final RecordingPresenter presenter = new RecordingPresenter();

        new LoginInteractor(dataAccess, presenter).execute(new LoginInputData("yidan", "wrong"));

        assertEquals("Incorrect password for \"yidan\".", presenter.failure);
        assertNull(dataAccess.currentUsername);
        assertNull(dataAccess.savedUser);
    }

    @Test
    void correctCredentialsSaveSessionAndSucceed() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        dataAccess.user = new StandardUser("yidan", "Yidan Xu", "password1", "Question?", "answer");
        final RecordingPresenter presenter = new RecordingPresenter();

        new LoginInteractor(dataAccess, presenter).execute(new LoginInputData("yidan", "password1"));

        assertNull(presenter.failure);
        assertEquals("yidan", presenter.success.getUsername());
        assertEquals("Yidan Xu", presenter.success.getDisplayName());
        assertEquals("yidan", dataAccess.currentUsername);
        assertSame(dataAccess.user, dataAccess.savedUser);
    }

    @Test
    void navigationMethodsDelegateToPresenter() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final LoginInteractor interactor = new LoginInteractor(new FakeDataAccess(), presenter);

        interactor.switchToSignUpView();
        interactor.switchToHomePageView();

        assertTrue(presenter.switchedToSignup);
        assertTrue(presenter.switchedToHome);
    }
}
