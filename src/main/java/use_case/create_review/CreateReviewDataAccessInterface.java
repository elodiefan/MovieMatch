package use_case.create_review;

import entity.Review;

/**
 * Data access interface for creating reviews.
 */
public interface CreateReviewDataAccessInterface {

    /**
     * Saves a newly created review.
     */
    void saveReview(Review review);
}
