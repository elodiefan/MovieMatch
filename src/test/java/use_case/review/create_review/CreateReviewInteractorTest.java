package use_case.review.create_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import entity.Review;

/**
 * Tests for the create review interactor.
 */
class CreateReviewInteractorTest {

    @Test
    void successSavesReviewWhenMediaIsInWatchHistory() {
        final RecordingReviewDao dao = new RecordingReviewDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                dao, (username, mediaId, mediaType) -> true, presenter);

        interactor.execute(42, " movie ", " Example Movie ", 2025,
                "/poster.jpg", " bob ", " Bob ", 85.0, " Enjoyed it ");

        assertNotNull(dao.savedReview);
        assertEquals("bob", dao.savedReview.getAuthorUsername());
        assertEquals(85.0, dao.savedReview.getRating());
        assertNotNull(presenter.success);
        assertNull(presenter.failure);
    }

    @Test
    void unwatchedMediaIsReportedAsFailure() {
        final RecordingReviewDao dao = new RecordingReviewDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                dao, (username, mediaId, mediaType) -> false, presenter);

        interactor.execute(42, "movie", "Example Movie", 2025,
                "/poster.jpg", "bob", "Bob", 85.0, "Enjoyed it");

        assertNull(dao.savedReview);
        assertNull(presenter.success);
        assertEquals("Please add this media to your watch history before writing a review.",
                presenter.failure);
    }

    private static final class RecordingReviewDao implements CreateReviewDataAccessInterface {
        private Review savedReview;

        @Override
        public void saveReview(final Review review) {
            savedReview = review;
        }
    }

    private static final class RecordingPresenter implements CreateReviewOutputBoundary {
        private CreateReviewOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(final CreateReviewOutputData review) {
            success = review;
        }

        @Override
        public String prepareFailView(final String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
