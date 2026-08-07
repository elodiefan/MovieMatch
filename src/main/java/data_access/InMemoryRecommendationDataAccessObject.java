package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.UserRating;

/** Holds ratings in memory, standing in for the review feature until it lands. */
public class InMemoryRecommendationDataAccessObject implements RecommendationDataAccessInterface {

    private final Map<String, List<UserRating>> ratingsByUser = new HashMap<>();
    private final Map<String, List<String>> friendsByUser = new HashMap<>();

    /** Creates an empty store. */
    public InMemoryRecommendationDataAccessObject() {
        // Ratings are added through recordRating.
    }

    /** Records that a user rated a title. */
    public void recordRating(final String username, final int mediaId, final double rating) {
        this.ratingsByUser.computeIfAbsent(username, key -> new ArrayList<>())
                .add(new UserRating(mediaId, rating));
    }

    /** Records a one-way friendship, so friends' ratings can be demonstrated before a real friend feature exists. */
    public void recordFriend(final String username, final String friendUsername) {
        this.friendsByUser.computeIfAbsent(username, key -> new ArrayList<>())
                .add(friendUsername);
    }

    @Override
    public List<UserRating> findRatingsByUser(final String username) {
        return new ArrayList<>(this.ratingsByUser.getOrDefault(username, new ArrayList<>()));
    }

    @Override
    public List<String> findFriendUsernames(final String username) {
        return new ArrayList<>(this.friendsByUser.getOrDefault(username, new ArrayList<>()));
    }

    @Override
    public List<UserRating> findFriendRatingsForMedia(final List<String> friendUsernames,
                                                      final int mediaId) {
        final List<UserRating> found = new ArrayList<>();
        for (final String friend : friendUsernames) {
            for (final UserRating rating : this.findRatingsByUser(friend)) {
                if (rating.getMediaId() == mediaId) {
                    found.add(rating);
                }
            }
        }
        return found;
    }
}
