package use_case.access_message_chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for {@link AccessMessageChatInteractor}. */
class AccessMessageChatInteractorTest {

    @Test
    void unblockedUsersCanAccessTheirChat() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final AccessMessageChatInteractor interactor = new AccessMessageChatInteractor(
                new UserAccess(false), (username, other) -> "Hello", presenter);

        interactor.execute(new AccessMessageChatInputData("alice"));

        assertNull(presenter.failure);
        assertTrue(presenter.output.canViewChat());
        assertEquals("bob", presenter.output.getUsername());
        assertEquals("alice", presenter.output.getOtherUsername());
        assertEquals("Hello", presenter.output.getDisplayText());
    }

    @Test
    void blockedUsersCannotAccessChat() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final AccessMessageChatInteractor interactor = new AccessMessageChatInteractor(
                new UserAccess(true), (username, other) -> "unused", presenter);

        interactor.execute(new AccessMessageChatInputData("alice"));

        assertNull(presenter.output);
        assertEquals("Cannot message this user.", presenter.failure);
    }

    /**
     * The production method is named canMessage, but currently returns true
     * when either user has blocked the other.
     */
    private static final class UserAccess implements AccessMessageChatUserDataAccessInterface {
        private final boolean blocked;

        private UserAccess(final boolean isBlocked) {
            blocked = isBlocked;
        }

        @Override
        public boolean canMessage(final String otherUsername) {
            return blocked;
        }

        @Override
        public String getCurrentUsername() {
            return "bob";
        }
    }

    private static final class RecordingPresenter implements AccessMessageChatOutputBoundary {
        private AccessMessageChatOutputData output;
        private String failure;

        @Override
        public void prepareAccessMessageChatSuccessView(
                final AccessMessageChatOutputData outputData) {
            output = outputData;
        }

        @Override
        public void prepareAccessMessageChatFailView(final String error) {
            failure = error;
        }
    }
}
