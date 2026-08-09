package use_case.recommendation;

import java.util.List;

/**
 * Data access interface for review ratings used by recommendations.
 */
public interface ReviewedMediaRatingDataAccessInterface {

    /**
     * Returns the user's review ratings in recommendation format.
     * @param username the username whose ratings are loaded
     * @return the user's review ratings
     */
    List<UserRating> findReviewRatingsByUser(String username);
}
