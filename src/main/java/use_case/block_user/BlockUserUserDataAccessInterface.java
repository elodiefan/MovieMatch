package use_case.block_user;

/** Data access interface for the Block User Use Case. */

public interface BlockUserUserDataAccessInterface {

    /** Checks whether the current user has already blocked the other user. */
    boolean alreadyBlocked(String otherUsername);

    /** Adds the other user to the current user's block list. */
    void addToBlockList(String otherUsername);

    /** Removes the other user from the current user's block list. */
    void removeFromBlockList(String otherUsername);
}
