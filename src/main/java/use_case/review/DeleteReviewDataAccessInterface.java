package use_case.review;

import java.util.Optional;

import entity.Review;

/**
 * Data access interface for deleting reviews.
 */
public interface DeleteReviewDataAccessInterface {

    /**
     * Gets one review by its id.
     * @param reviewId the id of the review to get
     * @return the review, if it exists
     */
    Optional<Review> getReviewById(String reviewId);

    /**
     * Deletes one review.
     * @param reviewId the id of the review to delete
     * @return true if the review was found and deleted
     */
    boolean deleteReview(String reviewId);
}
