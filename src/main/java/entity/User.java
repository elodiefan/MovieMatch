package entity;

import java.util.List;

/**
 * Interface representing a user of the app.
 */

public interface User {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getUsername();

    /**
     * Returns the display name of the user.
     * @return the display name of the user.
     */
    String getDisplayName();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the chosen security question of the user.
     * @return the chosen security question of the user.
     */
    String getSecurityQuestion();

    /**
     * Returns the answer to the user's chosen security question.
     * @return the answer to the user's chosen security question.
     */
    String getSecurityAnswer();

    /**
     * Returns the object storing all of the given user's lists: watchlist, watch history, reviews, and blocked users.
     * @return the object storing all of the given user's lists.
     */
    UserLists getUserLists();

    /**
     * Returns the user's watchlist.
     * @return the user's watchlist.
     */
    List<Integer> getWatchlist();

    /**
     * Returns the user's watch history.
     * @return the user's watch history.
     */
    List<Integer> getWatchHistory();

    /**
     * Returns the user's reviews.
     * @return the user's reviews.
     */
    List<Integer> getReviews();

    /**
     * Returns the user's list of blocked users.
     * @return the user's list of blocked users.
     */
    List<String> getBlockedUsers();
}
