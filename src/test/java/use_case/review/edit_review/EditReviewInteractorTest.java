package use_case.review.edit_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import entity.Review;

/**
 * Tests for the edit review interactor.
 */
class EditReviewInteractorTest {

    @Test
    void authorCanEditOwnReview() {
        final Review review = reviewByBob();
        final RecordingReviewDao dao = new RecordingReviewDao(review);
        final RecordingPresenter presenter = new RecordingPresenter();
        final EditReviewInteractor interactor = new EditReviewInteractor(dao, presenter);

        interactor.execute("review-1", "bob", 90.0, "Even better");

        assertTrue(dao.edited);
        assertEquals(90.0, review.getRating());
        assertEquals("Even better", review.getReviewText());
        assertNotNull(presenter.success);
        assertNull(presenter.failure);
    }

    @Test
    void anotherUserCannotEditReview() {
        final RecordingReviewDao dao = new RecordingReviewDao(reviewByBob());
        final RecordingPresenter presenter = new RecordingPresenter();
        final EditReviewInteractor interactor = new EditReviewInteractor(dao, presenter);

        interactor.execute("review-1", "alice", 90.0, "Changed");

        assertFalse(dao.edited);
        assertNull(presenter.success);
        assertEquals("Review could not be edited.", presenter.failure);
    }

    @Test
    void missingReviewIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new EditReviewInteractor(new RecordingReviewDao(null), presenter).execute(
                "review-1", "bob", 80.0, "Text");

        assertEquals("Review could not be edited.", presenter.failure);
    }

    @Test
    void blankReviewIdIsReportedAsFailure() {
        assertFailure(" ", "bob", 80.0, "Review id cannot be empty.");
    }

    @Test
    void blankUsernameIsReportedAsFailure() {
        assertFailure("review-1", " ", 80.0, "Username cannot be empty.");
    }

    @Test
    void invalidRatingIsReportedAsFailure() {
        assertFailure("review-1", "bob", -1.0,
                "Rating must be between 0 and 100.");
    }

    private static void assertFailure(final String reviewId, final String username,
                                      final double rating, final String expectedMessage) {
        final RecordingPresenter presenter = new RecordingPresenter();

        new EditReviewInteractor(new RecordingReviewDao(reviewByBob()), presenter).execute(
                reviewId, username, rating, "Text");

        assertEquals(expectedMessage, presenter.failure);
    }

    private static Review reviewByBob() {
        final ZonedDateTime time = ZonedDateTime.parse("2026-01-01T12:00:00-05:00");
        return new Review("review-1", 42, "movie", "Example Movie", "bob", "Bob",
                80.0, "Good", time, time, "moviematch", new HashSet<>());
    }

    private static final class RecordingReviewDao implements EditReviewDataAccessInterface {
        private final Review review;
        private boolean edited;

        private RecordingReviewDao(final Review inputReview) {
            review = inputReview;
        }

        @Override
        public Optional<Review> getReviewById(final String reviewId) {
            return Optional.ofNullable(review);
        }

        @Override
        public boolean editReview(final String reviewId, final double newRating,
                                  final String newReviewText,
                                  final ZonedDateTime newUpdatedAt) {
            edited = true;
            return true;
        }
    }

    private static final class RecordingPresenter implements EditReviewOutputBoundary {
        private EditReviewOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(final EditReviewOutputData review) {
            success = review;
        }

        @Override
        public String prepareFailView(final String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
