package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import use_case.comment.like_comment.LikeCommentInputData;
import use_case.comment.like_comment.LikeCommentInteractor;
import use_case.comment.like_comment.LikeCommentOutputBoundary;

class LikeCommentInteractorTest {

    @Test
    void validInputCallsDataAccessAndPresentsSuccess() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final String[] received = new String[2];
        final LikeCommentInteractor interactor = new LikeCommentInteractor((commentId, username) -> {
            received[0] = commentId;
            received[1] = username;
            return true;
        }, presenter);

        interactor.execute(new LikeCommentInputData(" comment-1 ", " bob "));

        assertEquals("comment-1", received[0]);
        assertEquals("bob", received[1]);
        assertTrue(presenter.success);
    }

    @Test
    void blankCommentIdPresentsFailureWithoutCallingDataAccess() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final boolean[] called = {false};
        final LikeCommentInteractor interactor = new LikeCommentInteractor((commentId, username) -> {
            called[0] = true;
            return true;
        }, presenter);

        interactor.execute(new LikeCommentInputData("  ", "bob"));

        assertEquals("Comment id cannot be empty.", presenter.failure);
        assertFalse(called[0]);
    }

    @Test
    void blankUsernamePresentsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new LikeCommentInteractor((id, username) -> true, presenter)
                .execute(new LikeCommentInputData("comment-1", " "));
        assertEquals("Username cannot be empty.", presenter.failure);
    }

    @Test
    void missingDataAccessIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new LikeCommentInteractor(null, presenter).execute(new LikeCommentInputData("comment-1", "bob"));
        assertEquals("Comment data access object has not been configured.", presenter.failure);
    }

    @Test
    void nullCommentIdIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new LikeCommentInteractor((id, username) -> true, presenter).execute(new LikeCommentInputData(null, "bob"));
        assertEquals("Comment id cannot be empty.", presenter.failure);
    }

    private static final class RecordingPresenter implements LikeCommentOutputBoundary {
        private boolean success;
        private String failure;

        @Override
        public void prepareSuccessView(boolean liked) {
            success = liked;
        }

        @Override
        public String prepareFailView(String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
