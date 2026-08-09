package use_case.get_lists.get_watchlist;

import entity.UserLists;

/**
 * Data access interface for the get watchlist use case.
 */
public interface GetWatchlistUserDataAccessInterface {

    /**
     * Returns the username of the current user.
     * @return the current username.
     */
    String getCurrentUsername();

    /**
     * Gets lists for the given user.
     * @param username the given user
     * @return the given user's lists
     */
    UserLists getLists(String username);
}
