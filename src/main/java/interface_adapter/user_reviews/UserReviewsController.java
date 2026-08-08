package interface_adapter.user_reviews;
import use_case.comment.get_user_comments.GetUserCommentsInputBoundary;
import use_case.review.delete_review.DeleteReviewInputBoundary;
import use_case.review.edit_review.EditReviewInputBoundary;
import use_case.review.get_user_reviews.GetUserReviewsInputBoundary;
import use_case.review.like_review.LikeReviewInputBoundary;
import use_case.review.unlike_review.UnlikeReviewInputBoundary;

/**
 * Controller for the user reviews view.
 */
public final class UserReviewsController {
    /**
     * The get user reviews interactor.
     */
    private final GetUserReviewsInputBoundary getUserReviewsInteractor;
    /**
     * The edit review interactor.
     */
    private final EditReviewInputBoundary editReviewInteractor;
    /**
     * The delete review interactor.
     */
    private final DeleteReviewInputBoundary deleteReviewInteractor;
    /**
     * The like review interactor.
     */
    private final LikeReviewInputBoundary likeReviewInteractor;
    /**
     * The unlike review interactor.
     */
    private final UnlikeReviewInputBoundary unlikeReviewInteractor;
    /**
     * The get user comments interactor.
     */
    private final GetUserCommentsInputBoundary getUserCommentsInteractor;

    /**
     * Creates a controller for user review actions.
     * @param inputGetUserReviewsInteractor the interactor for loading user
     * reviews
     * @param inputEditReviewInteractor the interactor for editing reviews
     * @param inputDeleteReviewInteractor the interactor for deleting reviews
     * @param inputLikeReviewInteractor the interactor for liking reviews
     * @param inputUnlikeReviewInteractor the interactor for unliking reviews
     */
    public UserReviewsController(
            final GetUserReviewsInputBoundary inputGetUserReviewsInteractor,
            final EditReviewInputBoundary inputEditReviewInteractor,
            final DeleteReviewInputBoundary inputDeleteReviewInteractor,
            final LikeReviewInputBoundary inputLikeReviewInteractor,
            final UnlikeReviewInputBoundary inputUnlikeReviewInteractor) {
        this(inputGetUserReviewsInteractor, inputEditReviewInteractor,
                inputDeleteReviewInteractor, inputLikeReviewInteractor,
                inputUnlikeReviewInteractor, null);
    }

    /**
     * Creates a controller for user review and comment actions.
     * @param inputGetUserReviewsInteractor the interactor for loading user
     * reviews
     * @param inputEditReviewInteractor the interactor for editing reviews
     * @param inputDeleteReviewInteractor the interactor for deleting reviews
     * @param inputLikeReviewInteractor the interactor for liking reviews
     * @param inputUnlikeReviewInteractor the interactor for unliking reviews
     * @param inputGetUserCommentsInteractor the interactor for loading user
     * comments
     */
    public UserReviewsController(
            final GetUserReviewsInputBoundary inputGetUserReviewsInteractor,
            final EditReviewInputBoundary inputEditReviewInteractor,
            final DeleteReviewInputBoundary inputDeleteReviewInteractor,
            final LikeReviewInputBoundary inputLikeReviewInteractor,
            final UnlikeReviewInputBoundary inputUnlikeReviewInteractor,
            final GetUserCommentsInputBoundary inputGetUserCommentsInteractor) {
        this.getUserReviewsInteractor = inputGetUserReviewsInteractor;
        this.editReviewInteractor = inputEditReviewInteractor;
        this.deleteReviewInteractor = inputDeleteReviewInteractor;
        this.likeReviewInteractor = inputLikeReviewInteractor;
        this.unlikeReviewInteractor = inputUnlikeReviewInteractor;
        this.getUserCommentsInteractor = inputGetUserCommentsInteractor;
    }

    /**
     * Loads persisted reviews written by one user.
     * @param username the username of the review author
     */
    public void loadUserReviews(final String username) {
        getUserReviewsInteractor.execute(username);
    }

    /**
     * Loads persisted comments written by one user.
     * @param username the username of the comment author
     */
    public void loadUserComments(final String username) {
        if (getUserCommentsInteractor != null) {
            getUserCommentsInteractor.execute(username);
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
        editReviewInteractor.execute(reviewId, username, newRating,
                newReviewText);
    }

    /**
     * Deletes a persisted user review.
     * @param reviewId the id of the review to delete
     * @param username the username of the user deleting the review
     */
    public void deleteReview(final String reviewId, final String username) {
        deleteReviewInteractor.execute(reviewId, username);
    }

    /**
     * Likes one persisted review.
     * @param reviewId the id of the review to like
     * @param username the username of the user liking the review
     */
    public void likeReview(final String reviewId, final String username) {
        likeReviewInteractor.execute(reviewId, username);
    }

    /**
     * Unlikes one persisted review.
     * @param reviewId the id of the review to unlike
     * @param username the username of the user unliking the review
     */
    public void unlikeReview(final String reviewId, final String username) {
        unlikeReviewInteractor.execute(reviewId, username);
    }
}
