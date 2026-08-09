package use_case.block_user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for {@link BlockUserInteractor}. */
class BlockUserInteractorTest {

    @Test
    void unblockedUserIsAddedToBlockList() {
        final RecordingDao dao = new RecordingDao();
        final RecordingPresenter presenter = new RecordingPresenter();

        new BlockUserInteractor(dao, presenter).execute(new BlockUserInputData("alice"));

        assertEquals("alice", dao.addedUsername);
        assertTrue(presenter.output.isOnBlockList());
        assertFalse(presenter.output.isUseCaseFailed());
    }

    @Test
    void blockedUserIsRemovedFromBlockList() {
        final RecordingDao dao = new RecordingDao();
        dao.alreadyBlocked = true;
        final RecordingPresenter presenter = new RecordingPresenter();

        new BlockUserInteractor(dao, presenter).execute(new BlockUserInputData("alice"));

        assertEquals("alice", dao.removedUsername);
        assertFalse(presenter.output.isOnBlockList());
    }

    private static final class RecordingDao implements BlockUserUserDataAccessInterface {
        private boolean alreadyBlocked;
        private String addedUsername;
        private String removedUsername;

        @Override
        public boolean alreadyBlocked(final String otherUsername) {
            return alreadyBlocked;
        }

        @Override
        public void addToBlockList(final String otherUsername) {
            addedUsername = otherUsername;
        }

        @Override
        public void removeFromBlockList(final String otherUsername) {
            removedUsername = otherUsername;
        }
    }

    private static final class RecordingPresenter implements BlockUserOutputBoundary {
        private BlockUserOutputData output;

        @Override
        public void prepareBlockSuccessView(final BlockUserOutputData outputData) {
            output = outputData;
        }
    }
}
