package use_case.review.edit_review;

import java.time.ZonedDateTime;
import java.util.Optional;

import entity.Review;

/**
 * Data access interface for editing reviews.
 */
public interface EditReviewDataAccessInterface {

    /**
     * Gets one review by its id.
     * @param reviewId the id of the review to get
     * @return the review, if it exists
     */
    Optional<Review> getReviewById(String reviewId);

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
}
