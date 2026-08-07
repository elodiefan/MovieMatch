package use_case.get_user_reviews;

import java.util.List;

import entity.Review;

/**
 * Data access interface for loading reviews written by one user.
 */
public interface GetUserReviewsDataAccessInterface {

    /**
     * Gets all reviews written by one user.
     */
    List<Review> getReviewsByUsername(String username);
}
