package use_case.review.delete_review;

import java.util.Optional;

import entity.Review;

/** Data access interface for deleting reviews. */
public interface DeleteReviewDataAccessInterface {

    /** Gets one review by its id. */
    Optional<Review> getReviewById(String reviewId);

    /** Deletes one review. */
    boolean deleteReview(String reviewId);
}
