package use_case.review.get_user_reviews;

import java.util.List;

import entity.Review;

/**
 * Data access interface for loading reviews written by one user.
 */
public interface GetUserReviewsDataAccessInterface {

    /**
     * Gets all reviews written by one user.
     * @param username the author's username
     * @return the reviews written by the user
     */
    List<Review> getReviewsByUsername(String username);
}
