package use_case.comment;

import java.util.Optional;

import entity.Review;

/**
 * Data access interface for loading reviews related to user comments.
 */
public interface GetUserCommentsReviewDataAccessInterface {

    /**
     * Gets one review by its id.
     * @param reviewId the id of the review to get
     * @return the review, if it exists
     */
    Optional<Review> getReviewById(String reviewId);
}
