package use_case.change_username;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import use_case.signup.SignupInteractor;

class ChangeUsernameInteractorTest {

    @Test
    void validAvailableUsernameIsSavedAndPresented() {
        final RecordingDataAccess dataAccess = new RecordingDataAccess(false);
        final RecordingPresenter presenter = new RecordingPresenter();

        new ChangeUsernameInteractor(dataAccess, presenter, validator()).changeUsername(
                new ChangeUsernameInputData("bob", "bob_new", "Bob"));

        assertEquals("bob", dataAccess.oldUsername);
        assertEquals("bob_new", dataAccess.newUsername);
        assertEquals("bob_new", presenter.success.getNewUsername());
        assertNull(presenter.failure);
    }

    @Test
    void existingUsernameIsRejectedWithoutSaving() {
        final RecordingDataAccess dataAccess = new RecordingDataAccess(true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new ChangeUsernameInteractor(dataAccess, presenter, validator()).changeUsername(
                new ChangeUsernameInputData("bob", "alice", "Bob"));

        assertEquals("Username already exists.", presenter.failure);
        assertNull(dataAccess.newUsername);
        assertNull(presenter.success);
    }

    private SignupInteractor validator() {
        return new SignupInteractor(null, null, null);
    }

    private static final class RecordingDataAccess
            implements ChangeUsernameUserDataAccessInterface {
        private final boolean exists;
        private String oldUsername;
        private String newUsername;

        private RecordingDataAccess(boolean exists) {
            this.exists = exists;
        }

        @Override
        public boolean existsByName(String username) {
            return exists;
        }

        @Override
        public void changeUsername(String username, String changedUsername) {
            oldUsername = username;
            newUsername = changedUsername;
        }
    }

    private static final class RecordingPresenter implements ChangeUsernameOutputBoundary {
        private ChangeUsernameOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(ChangeUsernameOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failure = errorMessage;
        }
    }
}
