package use_case.recommendation;

import java.util.List;

/** What the recommendation use case needs to know about people. */
public interface RecommendationDataAccessInterface {

    /** Returns every rating the given user has left. */
    List<UserRating> findRatingsByUser(String username);

    /** Returns the usernames of the given user's friends. */
    List<String> findFriendUsernames(String username);

    /** Returns what the given friends made of one title. */
    List<UserRating> findFriendRatingsForMedia(List<String> friendUsernames, int mediaId);
}
