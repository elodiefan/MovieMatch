package use_case.send_message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import entity.Message;

/** Tests for {@link SendMessageInteractor}. */
class SendMessageInteractorTest {

    @Test
    void nonBlankMessageIsSavedAndPresented() {
        final RecordingDao dao = new RecordingDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final LocalDateTime time = LocalDateTime.of(2026, 1, 1, 12, 0, 0, 123);

        new SendMessageInteractor(dao, presenter).execute(
                new SendMessageInputData("bob", "alice", "Hello", time));

        assertEquals("bob", dao.message.getSender());
        assertEquals("alice", dao.message.getRecipient());
        assertEquals(0, dao.message.getDate().getNano());
        assertEquals("Hello", presenter.output.getBody());
    }

    @Test
    void blankMessageIsIgnored() {
        final RecordingDao dao = new RecordingDao();
        final RecordingPresenter presenter = new RecordingPresenter();

        new SendMessageInteractor(dao, presenter).execute(
                new SendMessageInputData("bob", "alice", "   ", LocalDateTime.now()));

        assertNull(dao.message);
        assertNull(presenter.output);
    }

    @Test
    void switchToOtherAccountViewIsPassedToPresenter() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new SendMessageInteractor(new RecordingDao(), presenter).switchToOtherAccountView();

        assertTrue(presenter.switchedViews);
    }

    private static final class RecordingDao implements SendMessageMessageDataAccessInterface {
        private Message message;

        @Override
        public void addMessage(final Message inputMessage) {
            message = inputMessage;
        }
    }

    private static final class RecordingPresenter implements SendMessageOutputBoundary {
        private SendMessageOutputData output;
        private boolean switchedViews;

        @Override
        public void prepareSendMessageSuccessView(final SendMessageOutputData outputData) {
            output = outputData;
        }

        @Override
        public void switchToOtherAccountView() {
            switchedViews = true;
        }
    }
}
