package use_case.review.edit_review;

import java.time.ZonedDateTime;
import java.util.Optional;

import entity.Review;

/** Data access interface for editing reviews. */
public interface EditReviewDataAccessInterface {

    /** Gets one review by its id. */
    Optional<Review> getReviewById(String reviewId);

    /** Updates one review. */
    boolean editReview(String reviewId, double newRating, String newReviewText,
                       ZonedDateTime newUpdatedAt);
}
