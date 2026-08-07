package interface_adapter.media_reviews;

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
 * Controller for the media reviews panel.
 */
public final class MediaReviewsController {
    /** The get media reviews interactor. */
    private final GetMediaReviewsInputBoundary getMediaReviewsInteractor;
    /** The create review interactor. */
    private final CreateReviewInputBoundary createReviewInteractor;
    /** The edit review interactor. */
    private final EditReviewInputBoundary editReviewInteractor;
    /** The delete review interactor. */
    private final DeleteReviewInputBoundary deleteReviewInteractor;
    /** The like review interactor. */
    private final LikeReviewInputBoundary likeReviewInteractor;
    /** The unlike review interactor. */
    private final UnlikeReviewInputBoundary unlikeReviewInteractor;

    /**
     * Creates a controller for media review actions.
     */
    public MediaReviewsController(
            final GetMediaReviewsInputBoundary inputGetMediaReviewsInteractor,
            final CreateReviewInputBoundary inputCreateReviewInteractor,
            final EditReviewInputBoundary inputEditReviewInteractor,
            final DeleteReviewInputBoundary inputDeleteReviewInteractor,
            final LikeReviewInputBoundary inputLikeReviewInteractor,
            final UnlikeReviewInputBoundary inputUnlikeReviewInteractor) {
        this.getMediaReviewsInteractor = inputGetMediaReviewsInteractor;
        this.createReviewInteractor = inputCreateReviewInteractor;
        this.editReviewInteractor = inputEditReviewInteractor;
        this.deleteReviewInteractor = inputDeleteReviewInteractor;
        this.likeReviewInteractor = inputLikeReviewInteractor;
        this.unlikeReviewInteractor = inputUnlikeReviewInteractor;
    }

    /**
     * Loads persisted reviews for one media item.
     */
    public void loadMediaReviews(final int mediaId, final String mediaType) {
        getMediaReviewsInteractor.execute(new GetMediaReviewsInputData(mediaId,
                mediaType));
    }

    /**
     * Creates a review for one media item.
     */
    public void createReview(final int mediaId, final String mediaType,
                             final String mediaTitle,
                             final String authorUsername,
                             final String authorDisplayName,
                             final double rating,
                             final String reviewText) {
        createReviewInteractor.execute(new CreateReviewInputData(mediaId,
                mediaType, mediaTitle, authorUsername, authorDisplayName,
                rating, reviewText));
    }

    /**
     * Edits a persisted review written by the given user.
     */
    public void editReview(final String reviewId, final String username,
                           final double newRating,
                           final String newReviewText) {
        editReviewInteractor.execute(new EditReviewInputData(reviewId,
                username, newRating, newReviewText));
    }

    /**
     * Deletes one persisted review written by the given user.
     */
    public void deleteReview(final String reviewId, final String username) {
        deleteReviewInteractor.execute(new DeleteReviewInputData(reviewId,
                username));
    }

    /**
     * Likes one persisted review.
     */
    public void likeReview(final String reviewId, final String username) {
        likeReviewInteractor.execute(new LikeReviewInputData(reviewId,
                username));
    }

    /**
     * Unlikes one persisted review.
     */
    public void unlikeReview(final String reviewId, final String username) {
        unlikeReviewInteractor.execute(new UnlikeReviewInputData(reviewId,
                username));
    }
}
