package use_case.review;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import entity.Review;
import use_case.comment.GetUserCommentsReviewDataAccessInterface;
import use_case.review.create_review.CreateReviewDataAccessInterface;
import use_case.review.delete_review.DeleteReviewDataAccessInterface;
import use_case.review.edit_review.EditReviewDataAccessInterface;
import use_case.review.get_media_reviews.GetMediaReviewsDataAccessInterface;
import use_case.review.get_user_reviews.GetUserReviewsDataAccessInterface;
import use_case.review.like_review.LikeReviewDataAccessInterface;
import use_case.review.unlike_review.UnlikeReviewDataAccessInterface;

/**
 * Data access interface for review persistence.
 */
public interface ReviewDataAccessInterface extends
        CreateReviewDataAccessInterface,
        DeleteReviewDataAccessInterface,
        EditReviewDataAccessInterface,
        GetMediaReviewsDataAccessInterface,
        GetUserReviewsDataAccessInterface,
        GetUserCommentsReviewDataAccessInterface,
        LikeReviewDataAccessInterface,
        UnlikeReviewDataAccessInterface {

    /**
     * Saves a review.
     */
    void saveReview(Review review);

    /**
     * Checks whether a review exists by its id.
     */
    boolean existsByReviewId(String reviewId);

    /**
     * Gets one review by its id.
     */
    Optional<Review> getReviewById(String reviewId);

    /**
     * Gets all reviews written by one user.
     */
    List<Review> getReviewsByUsername(String username);

    /**
     * Gets all reviews for one media item.
     */
    List<Review> getReviewsByMedia(int mediaId, String mediaType);

    /**
     * Updates one review.
     */
    boolean editReview(String reviewId, double newRating, String newReviewText,
                       ZonedDateTime newUpdatedAt);

    /**
     * Deletes one review.
     */
    boolean deleteReview(String reviewId);

    /**
     * Adds a user's like to one review.
     */
    boolean likeReview(String reviewId, String username);

    /**
     * Removes a user's like from one review.
     */
    boolean unlikeReview(String reviewId, String username);

    /**
     * Gets all stored reviews.
     */
    List<Review> getAllReviews();
}
