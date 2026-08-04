package use_case.block_user;

/**
 * Data access interface for the Block User Use Case.
 */

public interface BlockUserUserDataAccessInterface {

    /**
     * Checks whether the current user has already blocked the other user.
     * @param otherUsername the username of the other user
     */
    void alreadyBlocked(String otherUsername);

    /**
     * Adds the other user to the current user's block list.
     * @param otherUsername the username of the other user
     */
    void addToBlockList(String otherUsername);

    /**
     * Removes the other user from the current user's block list.
     * @param otherUsername the username of the other user
     */
    void removeFromBlockList(String otherUsername);
}
