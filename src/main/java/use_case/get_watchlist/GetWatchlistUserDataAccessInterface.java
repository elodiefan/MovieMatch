package use_case.get_watchlist;

import entity.UserLists;

/**
 * DAO for the Account Use Case.
 */
public interface GetWatchlistUserDataAccessInterface {

    /**
     * Get lists of a given user.
     * @param username the given user.
     * @return the given user's watchlist.
     */
    UserLists getLists(String username);

    /**
     * Get watchlist of a given user.
     * @param username the given user.
     * @return the given user's watchlist.
     */
    String getWatchlist(String username);

//    List<Integer> getWatchlist();
//
//    List<Integer> getWatchHistory();
//
//    List<Integer> getReviews();
//
//    List<String> getBlockedUsers();
}
