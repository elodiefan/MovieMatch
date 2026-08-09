package use_case.review.delete_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Review;

class DeleteReviewInteractorTest {

    @Test
    void authorCanDeleteReview() {
        final Review review = reviewWrittenBy("bob");
        final RecordingDataAccess dataAccess = new RecordingDataAccess(review, true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new DeleteReviewInteractor(dataAccess, presenter).execute(" review-1 ", " bob ");

        assertEquals("review-1", dataAccess.deletedId);
        assertTrue(presenter.success);
    }

    @Test
    void differentUserCannotDeleteReview() {
        final RecordingDataAccess dataAccess = new RecordingDataAccess(
                reviewWrittenBy("alice"), true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new DeleteReviewInteractor(dataAccess, presenter).execute("review-1", "bob");

        assertEquals("Review could not be deleted.", presenter.failure);
        assertFalse(dataAccess.deleteCalled);
    }

    private Review reviewWrittenBy(String username) {
        final ZonedDateTime time = ZonedDateTime.now();
        return new Review("review-1", 101, "movie", "Example Movie",
                username, "Bob", 8.0, "Text", time, time, "user", Set.of());
    }

    private static final class RecordingDataAccess implements DeleteReviewDataAccessInterface {
        private final Review review;
        private final boolean deleteResult;
        private boolean deleteCalled;
        private String deletedId;

        private RecordingDataAccess(Review review, boolean deleteResult) {
            this.review = review;
            this.deleteResult = deleteResult;
        }

        @Override
        public Optional<Review> getReviewById(String reviewId) {
            return Optional.ofNullable(review);
        }

        @Override
        public boolean deleteReview(String reviewId) {
            deleteCalled = true;
            deletedId = reviewId;
            return deleteResult;
        }
    }

    private static final class RecordingPresenter implements DeleteReviewOutputBoundary {
        private boolean success;
        private String failure;

        @Override
        public void prepareSuccessView(boolean deleted) {
            success = deleted;
        }

        @Override
        public String prepareFailView(String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
