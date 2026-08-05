package use_case.get_lists;

import entity.UserLists;

/**
 * DAO for the Account Use Case.
 */
public interface GetListsUserDataAccessInterface {

    /**
     * Get lists of a given user.
     * @param username the given user.
     * @return the given user's watchlist.
     */
    UserLists getLists(String username);
}
