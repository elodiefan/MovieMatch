package use_case.recommendation;

import java.util.List;

/**
 * Data access interface for review ratings used by recommendations.
 */
public interface ReviewedMediaRatingDataAccessInterface {

    /**
     * Returns the user's review ratings in recommendation format.
     */
    List<UserRating> findReviewRatingsByUser(String username);
}
