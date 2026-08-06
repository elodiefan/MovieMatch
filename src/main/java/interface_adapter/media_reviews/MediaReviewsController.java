package interface_adapter.media_reviews;

import use_case.review.CreateReviewInputBoundary;
import use_case.review.CreateReviewInputData;
import use_case.review.DeleteReviewInputBoundary;
import use_case.review.DeleteReviewInputData;
import use_case.review.EditReviewInputBoundary;
import use_case.review.EditReviewInputData;
import use_case.review.GetMediaReviewsInputBoundary;
import use_case.review.GetMediaReviewsInputData;
import use_case.review.LikeReviewInputBoundary;
import use_case.review.LikeReviewInputData;
import use_case.review.UnlikeReviewInputBoundary;
import use_case.review.UnlikeReviewInputData;

/**
 * Controller for the media reviews panel.
 */
public class MediaReviewsController {
    private final GetMediaReviewsInputBoundary getMediaReviewsInteractor;
    private final CreateReviewInputBoundary createReviewInteractor;
    private final EditReviewInputBoundary editReviewInteractor;
    private final DeleteReviewInputBoundary deleteReviewInteractor;
    private final LikeReviewInputBoundary likeReviewInteractor;
    private final UnlikeReviewInputBoundary unlikeReviewInteractor;

    /**
     * Creates a controller for media review actions.
     * @param getMediaReviewsInteractor the interactor for loading reviews
     * @param createReviewInteractor the interactor for creating reviews
     * @param editReviewInteractor the interactor for editing reviews
     * @param deleteReviewInteractor the interactor for deleting reviews
     * @param likeReviewInteractor the interactor for liking reviews
     * @param unlikeReviewInteractor the interactor for unliking reviews
     */
    public MediaReviewsController(
            final GetMediaReviewsInputBoundary getMediaReviewsInteractor,
            final CreateReviewInputBoundary createReviewInteractor,
            final EditReviewInputBoundary editReviewInteractor,
            final DeleteReviewInputBoundary deleteReviewInteractor,
            final LikeReviewInputBoundary likeReviewInteractor,
            final UnlikeReviewInputBoundary unlikeReviewInteractor) {
        this.getMediaReviewsInteractor = getMediaReviewsInteractor;
        this.createReviewInteractor = createReviewInteractor;
        this.editReviewInteractor = editReviewInteractor;
        this.deleteReviewInteractor = deleteReviewInteractor;
        this.likeReviewInteractor = likeReviewInteractor;
        this.unlikeReviewInteractor = unlikeReviewInteractor;
    }

    /**
     * Loads persisted reviews for one media item.
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     */
    public void loadMediaReviews(final int mediaId, final String mediaType) {
        getMediaReviewsInteractor.execute(new GetMediaReviewsInputData(mediaId,
                mediaType));
    }

    /**
     * Creates a review for one media item.
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     * @param mediaTitle the reviewed media title
     * @param authorUsername the review author's username
     * @param authorDisplayName the review author's display name
     * @param rating the rating percentage
     * @param reviewText the written review text
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
     * Deletes one persisted review written by the given user.
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
