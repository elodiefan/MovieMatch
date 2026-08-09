package use_case.change_display_name;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ChangeDisplayNameInteractorTest {

    @Test
    void validDisplayNameIsSavedAndPresented() {
        final RecordingDataAccess dataAccess = new RecordingDataAccess(true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new ChangeDisplayNameInteractor(dataAccess, presenter).changeDisplayName(
                new ChangeDisplayNameInputData("bob", "Bob", "Bobby"));

        assertEquals("bob", dataAccess.changedUsername);
        assertEquals("Bobby", dataAccess.changedDisplayName);
        assertEquals("Bobby", presenter.success.getNewDisplayName());
        assertNull(presenter.failure);
    }

    @Test
    void blankDisplayNameIsRejectedWithoutSaving() {
        final RecordingDataAccess dataAccess = new RecordingDataAccess(true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new ChangeDisplayNameInteractor(dataAccess, presenter).changeDisplayName(
                new ChangeDisplayNameInputData("bob", "Bob", "  "));

        assertEquals("Display name cannot be empty.", presenter.failure);
        assertNull(dataAccess.changedDisplayName);
        assertNull(presenter.success);
    }

    private static final class RecordingDataAccess
            implements ChangeDisplayNameUserDataAccessInterface {
        private final boolean exists;
        private String changedUsername;
        private String changedDisplayName;

        private RecordingDataAccess(boolean exists) {
            this.exists = exists;
        }

        @Override
        public boolean existsByName(String username) {
            return exists;
        }

        @Override
        public void changeDisplayName(String username, String newDisplayName) {
            changedUsername = username;
            changedDisplayName = newDisplayName;
        }
    }

    private static final class RecordingPresenter implements ChangeDisplayNameOutputBoundary {
        private ChangeDisplayNameOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(ChangeDisplayNameOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failure = errorMessage;
        }
    }
}
