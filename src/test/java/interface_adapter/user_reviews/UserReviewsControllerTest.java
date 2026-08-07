package interface_adapter.user_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import use_case.comment.get_user_comments.GetUserCommentsInputBoundary;
import use_case.comment.get_user_comments.GetUserCommentsInputData;
import use_case.review.delete_review.DeleteReviewInputBoundary;
import use_case.review.delete_review.DeleteReviewInputData;
import use_case.review.edit_review.EditReviewInputBoundary;
import use_case.review.edit_review.EditReviewInputData;
import use_case.review.get_user_reviews.GetUserReviewsInputBoundary;
import use_case.review.get_user_reviews.GetUserReviewsInputData;
import use_case.review.like_review.LikeReviewInputBoundary;
import use_case.review.like_review.LikeReviewInputData;
import use_case.review.unlike_review.UnlikeReviewInputBoundary;
import use_case.review.unlike_review.UnlikeReviewInputData;

/**
 * Tests for the user reviews controller.
 */
public class UserReviewsControllerTest {

    @Test
    void loadUserReviewsAndCommentsPackageInputData() {
        final CapturingGetUserReviewsBoundary getReviewsBoundary =
                new CapturingGetUserReviewsBoundary();
        final CapturingGetUserCommentsBoundary getCommentsBoundary =
                new CapturingGetUserCommentsBoundary();
        final UserReviewsController controller = createController(
                getReviewsBoundary, new CapturingEditReviewBoundary(),
                new CapturingDeleteReviewBoundary(),
                new CapturingLikeReviewBoundary(),
                new CapturingUnlikeReviewBoundary(), getCommentsBoundary);

        controller.loadUserReviews("elodie");
        controller.loadUserComments("elodie");

        assertEquals("elodie", getReviewsBoundary.inputData.getUsername());
        assertEquals("elodie", getCommentsBoundary.inputData.getUsername());
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
        final UserReviewsController controller = createController(
                new CapturingGetUserReviewsBoundary(), editBoundary,
                deleteBoundary, likeBoundary, unlikeBoundary,
                new CapturingGetUserCommentsBoundary());

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

    private UserReviewsController createController(
            GetUserReviewsInputBoundary getBoundary,
            EditReviewInputBoundary editBoundary,
            DeleteReviewInputBoundary deleteBoundary,
            LikeReviewInputBoundary likeBoundary,
            UnlikeReviewInputBoundary unlikeBoundary,
            GetUserCommentsInputBoundary commentsBoundary) {
        return new UserReviewsController(getBoundary, editBoundary,
                deleteBoundary, likeBoundary, unlikeBoundary, commentsBoundary);
    }

    private static final class CapturingGetUserReviewsBoundary
            implements GetUserReviewsInputBoundary {
        private GetUserReviewsInputData inputData;

        @Override
        public void execute(GetUserReviewsInputData inputData) {
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

    private static final class CapturingGetUserCommentsBoundary
            implements GetUserCommentsInputBoundary {
        private GetUserCommentsInputData inputData;

        @Override
        public void execute(GetUserCommentsInputData inputData) {
            this.inputData = inputData;
        }
    }
}
