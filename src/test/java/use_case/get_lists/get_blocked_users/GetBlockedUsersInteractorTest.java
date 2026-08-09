package use_case.get_lists.get_blocked_users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import entity.UserLists;

/** Tests for {@link GetBlockedUsersInteractor}. */
class GetBlockedUsersInteractorTest {

    @Test
    void executeReturnsBlockedUsers() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new GetBlockedUsersInteractor(new RecordingDao(), presenter).execute(
                new GetBlockedUsersInputData("bob", "Bob"));

        assertEquals("bob", presenter.output.getUsername());
        assertEquals("Bob", presenter.output.getDisplayName());
        assertEquals("alice", presenter.output.getBlockedUsers());
    }

    @Test
    void currentUserReturnsToPersonalAccount() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetBlockedUsersInteractor interactor =
                new GetBlockedUsersInteractor(new RecordingDao(), presenter);

        interactor.switchToAccountView(new GetBlockedUsersInputData("bob", "Bob"));

        assertTrue(presenter.personalAccount);
        assertFalse(presenter.otherAccount);
    }

    @Test
    void anotherUserReturnsToOtherAccount() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetBlockedUsersInteractor interactor =
                new GetBlockedUsersInteractor(new RecordingDao(), presenter);

        interactor.switchToAccountView(new GetBlockedUsersInputData("alice", "Alice"));

        assertFalse(presenter.personalAccount);
        assertTrue(presenter.otherAccount);
    }

    private static final class RecordingDao
            implements GetBlockedUsersUserDataAccessInterface {
        @Override
        public String getCurrentUsername() {
            return "bob";
        }

        @Override
        public UserLists getLists(final String username) {
            return new UserLists(username, "", "", "alice");
        }
    }

    private static final class RecordingPresenter implements GetBlockedUsersOutputBoundary {
        private GetBlockedUsersOutputData output;
        private boolean personalAccount;
        private boolean otherAccount;

        @Override
        public void prepareSuccessView(final GetBlockedUsersOutputData response) {
            output = response;
        }

        @Override
        public void switchToPersonalAccountView() {
            personalAccount = true;
        }

        @Override
        public void switchToOtherAccountView() {
            otherAccount = true;
        }
    }
}
