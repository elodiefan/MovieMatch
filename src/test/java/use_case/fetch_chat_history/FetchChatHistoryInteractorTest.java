package use_case.fetch_chat_history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/** Tests for {@link FetchChatHistoryInteractor}. */
class FetchChatHistoryInteractorTest {

    @Test
    void existingChatWithoutDateFetchesAllNewMessages() {
        final RecordingDao dao = new RecordingDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final StringBuilder previous = new StringBuilder("Earlier\n");

        new FetchChatHistoryInteractor(dao, presenter).execute(
                new FetchChatHistoryInputData("bob", "alice", previous, null));

        assertEquals("Earlier\nNew", presenter.output.getDisplayText());
        assertFalse(presenter.output.isUseCaseFailed());
        assertEquals(1, dao.fetchWithoutDateCount);
    }

    @Test
    void existingChatWithDateUsesDatedFetch() {
        final RecordingDao dao = new RecordingDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final LocalDateTime date = LocalDateTime.of(2026, 1, 1, 12, 0);

        new FetchChatHistoryInteractor(dao, presenter).execute(
                new FetchChatHistoryInputData("bob", "alice", new StringBuilder(), date));

        assertEquals("Since date", presenter.output.getDisplayText());
        assertEquals(date, dao.dateReceived);
    }

    @Test
    void missingChatKeepsPreviousMessages() {
        final RecordingDao dao = new RecordingDao();
        dao.chatExists = false;
        final RecordingPresenter presenter = new RecordingPresenter();

        new FetchChatHistoryInteractor(dao, presenter).execute(
                new FetchChatHistoryInputData("bob", "alice",
                        new StringBuilder("Earlier"), null));

        assertEquals("Earlier", presenter.output.getDisplayText());
        assertEquals(0, dao.fetchWithoutDateCount);
    }

    private static final class RecordingDao
            implements FetchChatHistoryMessageDataAccessInterface {
        private boolean chatExists = true;
        private int fetchWithoutDateCount;
        private LocalDateTime dateReceived;

        @Override
        public boolean chatExists(final String username, final String otherUsername) {
            return chatExists;
        }

        @Override
        public String getNewMessages(final String username, final String otherUsername) {
            fetchWithoutDateCount++;
            return "New";
        }

        @Override
        public String getNewMessages(final String username, final String otherUsername,
                                     final LocalDateTime lastFetchTime) {
            dateReceived = lastFetchTime;
            return "Since date";
        }
    }

    private static final class RecordingPresenter implements FetchChatHistoryOutputBoundary {
        private FetchChatHistoryOutputData output;

        @Override
        public void prepareFetchChatHistorySuccessView(
                final FetchChatHistoryOutputData outputData) {
            output = outputData;
        }
    }
}
