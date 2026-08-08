package use_case.review;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import entity.Review;

/**
 * Data access interface for review persistence.
 */
public interface ReviewDataAccessInterface {

    /**
     * Saves a review.
     * @param review the review to save
     */
    void saveReview(Review review);

    /**
     * Checks whether a review exists by its id.
     * @param reviewId the id of the review to check
     * @return true if the review exists
     */
    boolean existsByReviewId(String reviewId);

    /**
     * Gets one review by its id.
     * @param reviewId the id of the review to get
     * @return the review, if it exists
     */
    Optional<Review> getReviewById(String reviewId);

    /**
     * Gets all reviews written by one user.
     * @param username the author's username
     * @return the reviews written by the user
     */
    List<Review> getReviewsByUsername(String username);

    /**
     * Gets all reviews for one media item.
     * @param mediaId the media id
     * @param mediaType the media type
     * @return the reviews for the media item
     */
    List<Review> getReviewsByMedia(int mediaId, String mediaType);

    /**
     * Updates one review.
     * @param reviewId the id of the review to edit
     * @param newRating the replacement rating
     * @param newReviewText the replacement review text
     * @param newUpdatedAt the updated timestamp
     * @return true if the review was found and edited
     */
    boolean editReview(String reviewId, double newRating, String newReviewText,
                       ZonedDateTime newUpdatedAt);

    /**
     * Deletes one review.
     * @param reviewId the id of the review to delete
     * @return true if the review was found and deleted
     */
    boolean deleteReview(String reviewId);

    /**
     * Adds a user's like to one review.
     * @param reviewId the id of the review to like
     * @param username the username of the user liking the review
     * @return true if the review was found and liked
     */
    boolean likeReview(String reviewId, String username);

    /**
     * Removes a user's like from one review.
     * @param reviewId the id of the review to unlike
     * @param username the username of the user unliking the review
     * @return true if the review was found and unliked
     */
    boolean unlikeReview(String reviewId, String username);

    /**
     * Gets all stored reviews.
     * @return all stored reviews
     */
    List<Review> getAllReviews();
}
