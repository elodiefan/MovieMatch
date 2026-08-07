package entity;

/** Interface representing a user of the app. */

public interface User {

    /** Returns the username of the user. */
    String getUsername();

    /** Returns the display name of the user. */
    String getDisplayName();

    /** Returns the password of the user. */
    String getPassword();

    /** Returns the chosen security question of the user. */
    String getSecurityQuestion();

    /** Returns the answer to the user's chosen security question. */
    String getAnswer();

    /**
     * Returns the object storing all of the given user's lists: watchlist, watch history, reviews, and blocked users.
     */
    UserLists getUserLists();

    /** Returns the user's watchlist. */
    String getWatchlist();

    /** Returns the user's watch history. */
    String getWatchHistory();

    /** Returns the user's list of blocked users. */
    String getBlockedUsers();

    /** Sets the user's user lists. */
    void setUserLists(UserLists userLists);
}
