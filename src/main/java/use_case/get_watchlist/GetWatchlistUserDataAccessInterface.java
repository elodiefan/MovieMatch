package use_case.get_watchlist;

import entity.UserLists;

/**
 * Data access interface for the get watchlist use case.
 */
public interface GetWatchlistUserDataAccessInterface {

    /**
     * Returns the username of the current user.
     */
    String getCurrentUsername();

    /**
     * Gets lists for the given user.
     */
    UserLists getLists(String username);
}
