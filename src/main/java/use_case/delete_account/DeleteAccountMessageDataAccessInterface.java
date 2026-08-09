package use_case.delete_account;

/**
 * Data access interface for the Delete Account Use Case.
 */
public interface DeleteAccountMessageDataAccessInterface {

    /**
     * Deletes the account of the current user.
     * @param username username of the current user
     */
    void deleteChatHistory(String username);
}
