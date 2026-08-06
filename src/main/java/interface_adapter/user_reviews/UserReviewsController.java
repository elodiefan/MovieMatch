package interface_adapter.user_reviews;
import use_case.comment.GetUserCommentsInputBoundary;
import use_case.comment.GetUserCommentsInputData;
import use_case.review.DeleteReviewInputBoundary;
import use_case.review.DeleteReviewInputData;
import use_case.review.EditReviewInputBoundary;
import use_case.review.EditReviewInputData;
import use_case.review.GetUserReviewsInputBoundary;
import use_case.review.GetUserReviewsInputData;
import use_case.review.LikeReviewInputBoundary;
import use_case.review.LikeReviewInputData;
import use_case.review.UnlikeReviewInputBoundary;
import use_case.review.UnlikeReviewInputData;

/**
 * Controller for the user reviews view.
 */
public class UserReviewsController {
    private final GetUserReviewsInputBoundary getUserReviewsInteractor;
    private final EditReviewInputBoundary editReviewInteractor;
    private final DeleteReviewInputBoundary deleteReviewInteractor;
    private final LikeReviewInputBoundary likeReviewInteractor;
    private final UnlikeReviewInputBoundary unlikeReviewInteractor;
    private final GetUserCommentsInputBoundary getUserCommentsInteractor;

    /**
     * Creates a controller for user review actions.
     * @param getUserReviewsInteractor the interactor for loading user reviews
     * @param editReviewInteractor the interactor for editing reviews
     * @param deleteReviewInteractor the interactor for deleting reviews
     * @param likeReviewInteractor the interactor for liking reviews
     * @param unlikeReviewInteractor the interactor for unliking reviews
     */
    public UserReviewsController(
            final GetUserReviewsInputBoundary getUserReviewsInteractor,
            final EditReviewInputBoundary editReviewInteractor,
            final DeleteReviewInputBoundary deleteReviewInteractor,
            final LikeReviewInputBoundary likeReviewInteractor,
            final UnlikeReviewInputBoundary unlikeReviewInteractor) {
        this(getUserReviewsInteractor, editReviewInteractor,
                deleteReviewInteractor, likeReviewInteractor,
                unlikeReviewInteractor, null);
    }

    /**
     * Creates a controller for user review and comment actions.
     * @param getUserReviewsInteractor the interactor for loading user reviews
     * @param editReviewInteractor the interactor for editing reviews
     * @param deleteReviewInteractor the interactor for deleting reviews
     * @param likeReviewInteractor the interactor for liking reviews
     * @param unlikeReviewInteractor the interactor for unliking reviews
     * @param getUserCommentsInteractor the interactor for loading user comments
     */
    public UserReviewsController(
            final GetUserReviewsInputBoundary getUserReviewsInteractor,
            final EditReviewInputBoundary editReviewInteractor,
            final DeleteReviewInputBoundary deleteReviewInteractor,
            final LikeReviewInputBoundary likeReviewInteractor,
            final UnlikeReviewInputBoundary unlikeReviewInteractor,
            final GetUserCommentsInputBoundary getUserCommentsInteractor) {
        this.getUserReviewsInteractor = getUserReviewsInteractor;
        this.editReviewInteractor = editReviewInteractor;
        this.deleteReviewInteractor = deleteReviewInteractor;
        this.likeReviewInteractor = likeReviewInteractor;
        this.unlikeReviewInteractor = unlikeReviewInteractor;
        this.getUserCommentsInteractor = getUserCommentsInteractor;
    }

    /**
     * Loads persisted reviews written by one user.
     * @param username the username of the review author
     */
    public void loadUserReviews(final String username) {
        getUserReviewsInteractor.execute(new GetUserReviewsInputData(username));
    }

    /**
     * Loads persisted comments written by one user.
     * @param username the username of the comment author
     */
    public void loadUserComments(final String username) {
        if (getUserCommentsInteractor != null) {
            getUserCommentsInteractor.execute(
                    new GetUserCommentsInputData(username));
        }
    }


    /**
     * Edits an existing review.
     * @param reviewId the id of the review to edit
     * @param username the username of the user editing the review
     * @param newRating the updated rating percentage
     * @param newReviewText the updated review text
     */
    public void editReview(final String reviewId, final String username,
                             final double newRating,
                             final String newReviewText) {
        editReviewInteractor.execute(new EditReviewInputData(reviewId,
                username, newRating, newReviewText));
    }

    /**
     * Deletes a persisted user review.
     * @param reviewId the id of the review to delete
     * @param username the username of the user deleting the review
     */
    public void deleteReview(final String reviewId, final String username) {
        deleteReviewInteractor.execute(new DeleteReviewInputData(reviewId,
                username));
    }

    /**
     * Likes one persisted review.
     * @param reviewId the id of the review to like
     * @param username the username of the user liking the review
     */
    public void likeReview(final String reviewId, final String username) {
        likeReviewInteractor.execute(new LikeReviewInputData(reviewId,
                username));
    }

    /**
     * Unlikes one persisted review.
     * @param reviewId the id of the review to unlike
     * @param username the username of the user unliking the review
     */
    public void unlikeReview(final String reviewId, final String username) {
        unlikeReviewInteractor.execute(new UnlikeReviewInputData(reviewId,
                username));
    }
}
