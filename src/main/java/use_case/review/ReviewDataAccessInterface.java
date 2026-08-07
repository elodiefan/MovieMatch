package use_case.review;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import entity.Review;

/** Data access interface for review persistence. */
public interface ReviewDataAccessInterface {

    /** Saves a review. */
    void saveReview(Review review);

    /** Checks whether a review exists by its id. */
    boolean existsByReviewId(String reviewId);

    /** Gets one review by its id. */
    Optional<Review> getReviewById(String reviewId);

    /** Gets all reviews written by one user. */
    List<Review> getReviewsByUsername(String username);

    /** Gets all reviews for one media item. */
    List<Review> getReviewsByMedia(int mediaId, String mediaType);

    /** Updates one review. */
    boolean editReview(String reviewId, double newRating, String newReviewText,
                       ZonedDateTime newUpdatedAt);

    /** Deletes one review. */
    boolean deleteReview(String reviewId);

    /** Adds a user's like to one review. */
    boolean likeReview(String reviewId, String username);

    /** Removes a user's like from one review. */
    boolean unlikeReview(String reviewId, String username);

    /** Gets all stored reviews. */
    List<Review> getAllReviews();
}
