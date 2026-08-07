package use_case.get_lists;

import entity.UserLists;

/** DAO for the Account Use Case. */
public interface GetListsUserDataAccessInterface {

    /** Returns the username of the current user. */
    String getCurrentUsername();

    /** Get lists of a given user. */
    UserLists getLists(String username);
}
