package data_access;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Review;
import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.UserRating;
import use_case.recommendation.WatchedMediaDataAccessInterface;
import use_case.review.ReviewDataAccessInterface;

/**
 * Turns what a user has actually done in MovieMatch into ratings the
 * recommendation algorithm can score with.
 * <p>
 * Implements an interface owned by the use case layer, and is assembled from
 * two other such interfaces, so nothing here reaches inward. Ratings come from
 * two places and are combined here rather than in the interactor, which should
 * not have to know that MovieMatch keeps reviews and watch lists separately.
 */
public class UserActivityRecommendationDataAccess
        implements RecommendationDataAccessInterface {

    /**
     * The score given to something watched or saved but never reviewed.
     * <p>
     * Adding a title to a list is a weaker signal than rating it, but it is
     * still a signal, and most users will have far more list entries than
     * reviews. Sitting slightly above the middle of a ten point scale says
     * "probably liked" without drowning out an explicit rating.
     */
    public static final double IMPLIED_RATING = 6.5;

    private final WatchedMediaDataAccessInterface watchedMediaDataAccess;
    private final ReviewDataAccessInterface reviewDataAccess;

    public UserActivityRecommendationDataAccess(
            WatchedMediaDataAccessInterface watchedMediaDataAccess,
            ReviewDataAccessInterface reviewDataAccess) {
        this.watchedMediaDataAccess = watchedMediaDataAccess;
        this.reviewDataAccess = reviewDataAccess;
    }

    @Override
    public List<UserRating> findRatingsByUser(String username) {
        // Keyed by media id so an explicit review always beats the implied
        // score for the same title, whichever order they are read in.
        final Map<Integer, Double> ratings = new LinkedHashMap<>();

        for (Integer mediaId : watchedMediaDataAccess.findEngagedMediaIds(username)) {
            ratings.put(mediaId, IMPLIED_RATING);
        }

        for (Review review : reviewDataAccess.getReviewsByUsername(username)) {
            ratings.put(review.getMediaId(), review.getRating());
        }

        final List<UserRating> userRatings = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : ratings.entrySet()) {
            userRatings.add(new UserRating(entry.getKey(), entry.getValue()));
        }
        return userRatings;
    }

    /**
     * MovieMatch has no friends feature, so this is empty by design.
     * <p>
     * The algorithm defines that case: the friends factor scores zero and the
     * remaining factors decide the ranking.
     */
    @Override
    public List<String> findFriendUsernames(String username) {
        return new ArrayList<>();
    }

    @Override
    public List<UserRating> findFriendRatingsForMedia(List<String> friendUsernames, int mediaId) {
        return new ArrayList<>();
    }

    /**
     * Returns the titles a user has already engaged with, so they can be kept
     * out of their own recommendations.
     */
    public Set<Integer> engagedMediaIds(String username) {
        return watchedMediaDataAccess.findEngagedMediaIds(username);
    }
}
