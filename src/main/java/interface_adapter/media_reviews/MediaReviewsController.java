package interface_adapter.media_reviews;

import java.util.List;

import entity.Review;
import use_case.review.CreateReviewInteractor;
import use_case.review.DeleteReviewInteractor;
import use_case.review.EditReviewInteractor;
import use_case.review.GetMediaReviewsInteractor;
import use_case.review.LikeReviewInteractor;
import use_case.review.UnlikeReviewInteractor;

/**
 * Controller for the media reviews panel.
 */
public class MediaReviewsController {
    private final GetMediaReviewsInteractor getMediaReviewsInteractor;
    private final CreateReviewInteractor createReviewInteractor;
    private final EditReviewInteractor editReviewInteractor;
    private final DeleteReviewInteractor deleteReviewInteractor;
    private final LikeReviewInteractor likeReviewInteractor;
    private final UnlikeReviewInteractor unlikeReviewInteractor;

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
            final GetMediaReviewsInteractor getMediaReviewsInteractor,
            final CreateReviewInteractor createReviewInteractor,
            final EditReviewInteractor editReviewInteractor,
            final DeleteReviewInteractor deleteReviewInteractor,
            final LikeReviewInteractor likeReviewInteractor,
            final UnlikeReviewInteractor unlikeReviewInteractor) {
        this.getMediaReviewsInteractor = getMediaReviewsInteractor;
        this.createReviewInteractor = createReviewInteractor;
        this.editReviewInteractor = editReviewInteractor;
        this.deleteReviewInteractor = deleteReviewInteractor;
        this.likeReviewInteractor = likeReviewInteractor;
        this.unlikeReviewInteractor = unlikeReviewInteractor;
    }

    /**
     * Loads reviews for one media item.
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     * @param reviews the reviews to search through
     * @return the matching media reviews
     */
    public List<Review> getMediaReviews(final int mediaId,
                                        final String mediaType,
                                        final List<Review> reviews) {
        return getMediaReviewsInteractor.getMediaReviews(mediaId, mediaType,
                reviews);
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
     * @return the created review
     */
    public Review createReview(final int mediaId, final String mediaType,
                               final String mediaTitle,
                               final String authorUsername,
                               final String authorDisplayName,
                               final double rating,
                               final String reviewText) {
        return createReviewInteractor.createReview(mediaId, mediaType,
                mediaTitle, authorUsername, authorDisplayName, rating,
                reviewText);
    }

    /**
     * Edits an existing review.
     * @param review the review to edit
     * @param newRating the updated rating percentage
     * @param newReviewText the updated review text
     * @return the edited review
     */
    public Review editReview(final Review review, final double newRating,
                             final String newReviewText) {
        return editReviewInteractor.editReview(review, newRating,
                newReviewText);
    }

    /**
     * Deletes one review written by the given user.
     * @param reviewId the id of the review to delete
     * @param username the username of the user deleting the review
     * @param reviews the reviews to search through
     * @return true if the review was deleted
     */
    public boolean deleteReview(final String reviewId, final String username,
                                final List<Review> reviews) {
        return deleteReviewInteractor.deleteReview(reviewId, username,
                reviews);
    }

    /**
     * Likes a review.
     * @param reviewId the id of the review to like
     * @param username the username of the user liking the review
     * @param reviews the reviews to search through
     * @return true if the review was found and liked
     */
    public boolean likeReview(final String reviewId, final String username,
                              final List<Review> reviews) {
        return likeReviewInteractor.likeReview(reviewId, username, reviews);
    }

    /**
     * Unlikes a review.
     * @param reviewId the id of the review to unlike
     * @param username the username of the user unliking the review
     * @param reviews the reviews to search through
     * @return true if the review was found and unliked
     */
    public boolean unlikeReview(final String reviewId, final String username,
                                final List<Review> reviews) {
        return unlikeReviewInteractor.unlikeReview(reviewId, username,
                reviews);
    }
}
