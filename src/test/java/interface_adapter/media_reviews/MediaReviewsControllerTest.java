package interface_adapter.media_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import use_case.review.create_review.CreateReviewInputBoundary;
import use_case.review.create_review.CreateReviewInputData;
import use_case.review.delete_review.DeleteReviewInputBoundary;
import use_case.review.delete_review.DeleteReviewInputData;
import use_case.review.edit_review.EditReviewInputBoundary;
import use_case.review.edit_review.EditReviewInputData;
import use_case.review.get_media_reviews.GetMediaReviewsInputBoundary;
import use_case.review.get_media_reviews.GetMediaReviewsInputData;
import use_case.review.like_review.LikeReviewInputBoundary;
import use_case.review.like_review.LikeReviewInputData;
import use_case.review.unlike_review.UnlikeReviewInputBoundary;
import use_case.review.unlike_review.UnlikeReviewInputData;

/**
 * Tests for the media reviews controller.
 */
public class MediaReviewsControllerTest {

    @Test
    void loadMediaReviewsPackagesInputData() {
        final CapturingGetMediaReviewsBoundary getBoundary =
                new CapturingGetMediaReviewsBoundary();
        final MediaReviewsController controller = createController(getBoundary,
                new CapturingCreateReviewBoundary(),
                new CapturingEditReviewBoundary(),
                new CapturingDeleteReviewBoundary(),
                new CapturingLikeReviewBoundary(),
                new CapturingUnlikeReviewBoundary());

        controller.loadMediaReviews(550, "movie");

        assertEquals(550, getBoundary.inputData.getMediaId());
        assertEquals("movie", getBoundary.inputData.getMediaType());
    }

    @Test
    void createReviewPackagesInputData() {
        final CapturingCreateReviewBoundary createBoundary =
                new CapturingCreateReviewBoundary();
        final MediaReviewsController controller = createController(
                new CapturingGetMediaReviewsBoundary(), createBoundary,
                new CapturingEditReviewBoundary(),
                new CapturingDeleteReviewBoundary(),
                new CapturingLikeReviewBoundary(),
                new CapturingUnlikeReviewBoundary());

        controller.createReview(550, "movie", "Fight Club", "elodie",
                "Elodie", 92.0, "Great.");

        assertEquals(550, createBoundary.inputData.getMediaId());
        assertEquals("movie", createBoundary.inputData.getMediaType());
        assertEquals("Fight Club", createBoundary.inputData.getMediaTitle());
        assertEquals("elodie", createBoundary.inputData.getAuthorUsername());
        assertEquals("Elodie",
                createBoundary.inputData.getAuthorDisplayName());
        assertEquals(92.0, createBoundary.inputData.getRating());
        assertEquals("Great.", createBoundary.inputData.getReviewText());
    }

    @Test
    void editDeleteLikeAndUnlikePackageInputData() {
        final CapturingEditReviewBoundary editBoundary =
                new CapturingEditReviewBoundary();
        final CapturingDeleteReviewBoundary deleteBoundary =
                new CapturingDeleteReviewBoundary();
        final CapturingLikeReviewBoundary likeBoundary =
                new CapturingLikeReviewBoundary();
        final CapturingUnlikeReviewBoundary unlikeBoundary =
                new CapturingUnlikeReviewBoundary();
        final MediaReviewsController controller = createController(
                new CapturingGetMediaReviewsBoundary(),
                new CapturingCreateReviewBoundary(), editBoundary,
                deleteBoundary, likeBoundary, unlikeBoundary);

        controller.editReview("review-1", "elodie", 80.0, "Updated.");
        controller.deleteReview("review-2", "lily");
        controller.likeReview("review-3", "enzo");
        controller.unlikeReview("review-4", "yidan");

        assertEquals("review-1", editBoundary.inputData.getReviewId());
        assertEquals("elodie", editBoundary.inputData.getUsername());
        assertEquals(80.0, editBoundary.inputData.getRating());
        assertEquals("Updated.", editBoundary.inputData.getReviewText());
        assertEquals("review-2", deleteBoundary.inputData.getReviewId());
        assertEquals("lily", deleteBoundary.inputData.getUsername());
        assertEquals("review-3", likeBoundary.inputData.getReviewId());
        assertEquals("enzo", likeBoundary.inputData.getUsername());
        assertEquals("review-4", unlikeBoundary.inputData.getReviewId());
        assertEquals("yidan", unlikeBoundary.inputData.getUsername());
    }

    private MediaReviewsController createController(
            GetMediaReviewsInputBoundary getBoundary,
            CreateReviewInputBoundary createBoundary,
            EditReviewInputBoundary editBoundary,
            DeleteReviewInputBoundary deleteBoundary,
            LikeReviewInputBoundary likeBoundary,
            UnlikeReviewInputBoundary unlikeBoundary) {
        return new MediaReviewsController(getBoundary, createBoundary,
                editBoundary, deleteBoundary, likeBoundary, unlikeBoundary);
    }

    private static final class CapturingGetMediaReviewsBoundary
            implements GetMediaReviewsInputBoundary {
        private GetMediaReviewsInputData inputData;

        @Override
        public void execute(GetMediaReviewsInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingCreateReviewBoundary
            implements CreateReviewInputBoundary {
        private CreateReviewInputData inputData;

        @Override
        public void execute(CreateReviewInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingEditReviewBoundary
            implements EditReviewInputBoundary {
        private EditReviewInputData inputData;

        @Override
        public void execute(EditReviewInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingDeleteReviewBoundary
            implements DeleteReviewInputBoundary {
        private DeleteReviewInputData inputData;

        @Override
        public void execute(DeleteReviewInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingLikeReviewBoundary
            implements LikeReviewInputBoundary {
        private LikeReviewInputData inputData;

        @Override
        public void execute(LikeReviewInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingUnlikeReviewBoundary
            implements UnlikeReviewInputBoundary {
        private UnlikeReviewInputData inputData;

        @Override
        public void execute(UnlikeReviewInputData inputData) {
            this.inputData = inputData;
        }
    }
}
