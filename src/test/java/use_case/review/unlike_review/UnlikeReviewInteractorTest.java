package use_case.review.unlike_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UnlikeReviewInteractorTest {

    @Test
    void validInputCallsDataAccessAndPresentsSuccess() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final String[] received = new String[2];
        final UnlikeReviewInteractor interactor = new UnlikeReviewInteractor((reviewId, username) -> {
            received[0] = reviewId;
            received[1] = username;
            return true;
        }, presenter);

        interactor.execute(" review-1 ", " bob ");

        assertEquals("review-1", received[0]);
        assertEquals("bob", received[1]);
        assertTrue(presenter.success);
    }

    @Test
    void blankUsernamePresentsFailureWithoutCallingDataAccess() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final boolean[] called = {false};
        final UnlikeReviewInteractor interactor = new UnlikeReviewInteractor((reviewId, username) -> {
            called[0] = true;
            return true;
        }, presenter);

        interactor.execute("review-1", "  ");

        assertEquals("Username cannot be empty.", presenter.failure);
        assertFalse(called[0]);
    }

    private static final class RecordingPresenter implements UnlikeReviewOutputBoundary {
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
