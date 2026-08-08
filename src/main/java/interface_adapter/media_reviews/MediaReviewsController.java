package interface_adapter.media_reviews;

import use_case.create_review.CreateReviewInputBoundary;
import use_case.delete_review.DeleteReviewInputBoundary;
import use_case.edit_review.EditReviewInputBoundary;
import use_case.get_media_reviews.GetMediaReviewsInputBoundary;
import use_case.like_review.LikeReviewInputBoundary;
import use_case.unlike_review.UnlikeReviewInputBoundary;

/**
 * Controller for the media reviews panel.
 */
public final class MediaReviewsController {
    /**
     * The get media reviews interactor.
     */
    private final GetMediaReviewsInputBoundary getMediaReviewsInteractor;
    /**
     * The create review interactor.
     */
    private final CreateReviewInputBoundary createReviewInteractor;
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
     * Creates a controller for media review actions.
     * @param inputGetMediaReviewsInteractor the interactor for loading reviews
     * @param inputCreateReviewInteractor the interactor for creating reviews
     * @param inputEditReviewInteractor the interactor for editing reviews
     * @param inputDeleteReviewInteractor the interactor for deleting reviews
     * @param inputLikeReviewInteractor the interactor for liking reviews
     * @param inputUnlikeReviewInteractor the interactor for unliking reviews
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
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     */
    public void loadMediaReviews(final int mediaId, final String mediaType) {
        getMediaReviewsInteractor.execute(mediaId, mediaType);
    }

    /**
     * Creates a review for one media item.
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     * @param mediaTitle the reviewed media title
     * @param releaseYear the reviewed media release year
     * @param posterPath the reviewed media poster path
     * @param authorUsername the review author's username
     * @param authorDisplayName the review author's display name
     * @param rating the rating percentage
     * @param reviewText the written review text
     */
    public void createReview(final int mediaId, final String mediaType,
                             final String mediaTitle,
                             final int releaseYear,
                             final String posterPath,
                             final String authorUsername,
                             final String authorDisplayName,
                             final double rating,
                             final String reviewText) {
        createReviewInteractor.execute(mediaId, mediaType, mediaTitle,
                releaseYear, posterPath, authorUsername, authorDisplayName,
                rating, reviewText);
    }

    /**
     * Checks whether the user may start writing a review.
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     * @param authorUsername the review author's username
     * @return true if the user may write a review
     */
    public boolean canCreateReview(final int mediaId, final String mediaType,
                                   final String authorUsername) {
        return createReviewInteractor.canCreateReview(mediaId, mediaType,
                authorUsername);
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
        editReviewInteractor.execute(reviewId, username, newRating,
                newReviewText);
    }

    /**
     * Deletes one persisted review written by the given user.
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
