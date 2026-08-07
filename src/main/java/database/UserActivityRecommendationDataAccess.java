package database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.ReviewedMediaRatingDataAccessInterface;
import use_case.recommendation.UserRating;
import use_case.recommendation.WatchedMediaDataAccessInterface;

/** Turns a user's activity into ratings the algorithm can score with. */
public class UserActivityRecommendationDataAccess
        implements RecommendationDataAccessInterface {

    /** The score given to something watched or saved but never reviewed. */
    public static final double IMPLIED_RATING = 6.5;

    private final WatchedMediaDataAccessInterface watchedMediaDataAccess;
    private final ReviewedMediaRatingDataAccessInterface reviewDataAccess;

    public UserActivityRecommendationDataAccess(
            WatchedMediaDataAccessInterface watchedMediaDataAccess,
            ReviewedMediaRatingDataAccessInterface reviewDataAccess) {
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

        for (UserRating reviewRating
                : reviewDataAccess.findReviewRatingsByUser(username)) {
            ratings.put(reviewRating.getMediaId(), reviewRating.getRating());
        }

        final List<UserRating> userRatings = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : ratings.entrySet()) {
            userRatings.add(new UserRating(entry.getKey(), entry.getValue()));
        }
        return userRatings;
    }

    /** MovieMatch has no friends feature, so this is empty by design. */
    @Override
    public List<String> findFriendUsernames(String username) {
        return new ArrayList<>();
    }

    @Override
    public List<UserRating> findFriendRatingsForMedia(List<String> friendUsernames, int mediaId) {
        return new ArrayList<>();
    }

    /** Titles the user already saved or watched, so they are not suggested again. */
    public Set<Integer> engagedMediaIds(String username) {
        return watchedMediaDataAccess.findEngagedMediaIds(username);
    }
}
