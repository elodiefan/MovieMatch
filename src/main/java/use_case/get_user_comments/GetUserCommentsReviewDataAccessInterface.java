package use_case.get_user_comments;

import java.util.Optional;

import entity.Review;

/**
 * Data access interface for loading reviews related to user comments.
 */
public interface GetUserCommentsReviewDataAccessInterface {

    /**
     * Gets one review by its id.
     */
    Optional<Review> getReviewById(String reviewId);
}
