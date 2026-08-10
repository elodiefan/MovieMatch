package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import use_case.comment.unlike_comment.UnlikeCommentInputData;
import use_case.comment.unlike_comment.UnlikeCommentInteractor;
import use_case.comment.unlike_comment.UnlikeCommentOutputBoundary;

class UnlikeCommentInteractorTest {

    @Test
    void validInputCallsDataAccessAndPresentsSuccess() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final String[] received = new String[2];
        final UnlikeCommentInteractor interactor = new UnlikeCommentInteractor((commentId, username) -> {
            received[0] = commentId;
            received[1] = username;
            return true;
        }, presenter);

        interactor.execute(new UnlikeCommentInputData(" comment-1 ", " bob "));

        assertEquals("comment-1", received[0]);
        assertEquals("bob", received[1]);
        assertTrue(presenter.success);
    }

    @Test
    void blankUsernamePresentsFailureWithoutCallingDataAccess() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final boolean[] called = {false};
        final UnlikeCommentInteractor interactor = new UnlikeCommentInteractor((commentId, username) -> {
            called[0] = true;
            return true;
        }, presenter);

        interactor.execute(new UnlikeCommentInputData("comment-1", "  "));

        assertEquals("Username cannot be empty.", presenter.failure);
        assertFalse(called[0]);
    }

    @Test
    void blankCommentIdPresentsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new UnlikeCommentInteractor((id, username) -> true, presenter)
                .execute(new UnlikeCommentInputData(" ", "bob"));
        assertEquals("Comment id cannot be empty.", presenter.failure);
    }

    @Test
    void missingDataAccessIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new UnlikeCommentInteractor(null, presenter).execute(new UnlikeCommentInputData("comment-1", "bob"));
        assertEquals("Comment data access object has not been configured.", presenter.failure);
    }

    @Test
    void nullUsernameIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new UnlikeCommentInteractor((id, username) -> true, presenter)
                .execute(new UnlikeCommentInputData("comment-1", null));
        assertEquals("Username cannot be empty.", presenter.failure);
    }

    private static final class RecordingPresenter implements UnlikeCommentOutputBoundary {
        private boolean success;
        private String failure;

        @Override
        public void prepareSuccessView(boolean unliked) {
            success = unliked;
        }

        @Override
        public String prepareFailView(String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
