package use_case.get_profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class GetProfileInteractorTest {

    @Test
    void currentUserGoesToPersonalAccount() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetProfileInteractor interactor = new GetProfileInteractor(
                new ProfileDataAccess("bob"), presenter);

        interactor.execute(new GetProfileInputData("bob", "Old name"));

        assertEquals("bob", presenter.personal.getUsername());
        assertEquals("Bob", presenter.personal.getDisplayName());
        assertNull(presenter.other);
    }

    @Test
    void differentUserGoesToOtherAccount() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetProfileInteractor interactor = new GetProfileInteractor(
                new ProfileDataAccess("alice"), presenter);

        interactor.execute(new GetProfileInputData("bob", "Old name"));

        assertEquals("bob", presenter.other.getUsername());
        assertNull(presenter.personal);
    }

    private static final class ProfileDataAccess implements GetProfileUserDataAccessInterface {
        private final String currentUsername;

        private ProfileDataAccess(String currentUsername) {
            this.currentUsername = currentUsername;
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public String getDisplayName(String username) {
            return "Bob";
        }

        @Override
        public boolean canMessage(String username) {
            return true;
        }
    }

    private static final class RecordingPresenter implements GetProfileOutputBoundary {
        private GetProfileOutputData personal;
        private GetProfileOutputData other;

        @Override
        public void switchToPersonalAccountView(GetProfileOutputData response) {
            personal = response;
        }

        @Override
        public void switchToOtherAccountView(GetProfileOutputData response) {
            other = response;
        }
    }
}
