package use_case.recommendation;

import java.util.List;

/**
 * What the recommendation use case needs to know about people.
 */
public interface RecommendationDataAccessInterface {

    /**
     * Returns every rating the given user has left.
     *
     * @param username the username
     * @return the find ratings by user
     */
    List<UserRating> findRatingsByUser(String username);

    /**
     * Returns the usernames of the given user's friends.
     *
     * @param username the username
     * @return the find friend usernames
     */
    List<String> findFriendUsernames(String username);

    /**
     * Returns what the given friends made of one title.
     *
     * @param friendUsernames the friend usernames
     * @param mediaId the media id
     * @return the find friend ratings for media
     */
    List<UserRating> findFriendRatingsForMedia(List<String> friendUsernames, int mediaId);
}
