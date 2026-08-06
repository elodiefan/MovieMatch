package use_case.review;

import entity.Review;

/**
 * Data access interface for creating reviews.
 */
public interface CreateReviewDataAccessInterface {

    /**
     * Saves a newly created review.
     * @param review the review to save
     */
    void saveReview(Review review);
}
