package use_case.get_blocked_users;

import entity.UserLists;

/**
 * Data access interface for the get blocked users use case.
 */
public interface GetBlockedUsersUserDataAccessInterface {

    /**
     * Returns the username of the current user.
     * @return the current username
     */
    String getCurrentUsername();

    /**
     * Gets lists for the given user.
     * @param username the username
     * @return the user's lists
     */
    UserLists getLists(String username);
}
