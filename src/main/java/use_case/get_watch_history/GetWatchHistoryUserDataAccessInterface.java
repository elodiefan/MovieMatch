package use_case.get_watch_history;

import entity.UserLists;

/**
 * Data access interface for the get watch history use case.
 */
public interface GetWatchHistoryUserDataAccessInterface {

    /**
     * Returns the username of the current user.
     */
    String getCurrentUsername();

    /**
     * Gets lists for the given user.
     */
    UserLists getLists(String username);
}
