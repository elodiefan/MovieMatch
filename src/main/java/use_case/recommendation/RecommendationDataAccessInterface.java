package use_case.recommendation;

import java.util.List;

/**
 * What the recommendation use case needs to know about people.
 * <p>
 * Declared here, in the use case layer, and implemented further out — so the
 * interactor never depends on where ratings actually live. Today that is an
 * in-memory stand-in; once the review feature lands it will be MongoDB, and
 * nothing in this package has to change.
 */
public interface RecommendationDataAccessInterface {

    /**
     * Returns every rating the given user has left.
     *
     * @param username the user whose ratings are wanted
     * @return their ratings, empty if they have not rated anything
     */
    List<UserRating> findRatingsByUser(String username);

    /**
     * Returns the usernames of the given user's friends.
     * <p>
     * The app has no concept of friends yet, so implementations may return an
     * empty list. The algorithm defines that case: the friends factor scores
     * zero and the remaining factors decide the ranking.
     *
     * @param username the user whose friends are wanted
     * @return their friends' usernames, possibly empty
     */
    List<String> findFriendUsernames(String username);

    /**
     * Returns what the given friends made of one title.
     *
     * @param friendUsernames the friends to ask about
     * @param mediaId the title in question
     * @return the ratings those friends gave it, skipping any who have not rated it
     */
    List<UserRating> findFriendRatingsForMedia(List<String> friendUsernames, int mediaId);
}
