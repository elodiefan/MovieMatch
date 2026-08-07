package use_case.comment.unlike_comment.delete_account;

import entity.User;

/**
 * Data access interface for the Delete Account Use Case.
 */
public interface DeleteAccountUserDataAccessInterface {

    /**
     * Deletes the account of the current user.
     */
    void deleteAccount(User user);

    /**
     * Sets the current username for the application.
     */
    void setCurrentUsername(String username);

    /**
     * Return the security answer for the current user.
     */
    String getCurrentSecurityAnswer();
}
