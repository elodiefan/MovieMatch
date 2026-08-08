package use_case.delete_account;

import entity.User;

/**
 * Data access interface for the Delete Account Use Case.
 */
public interface DeleteAccountUserDataAccessInterface {

    /**
     * Deletes the account of the current user.
     * @param user the current user
     */
    void deleteAccount(User user);

    /**
     * Sets the current username for the application.
     * @param username the current username
     */
    void setCurrentUsername(String username);

    /**
     * Return the security answer for the current user.
     * @return the current user's security answer
     */
    String getCurrentSecurityAnswer();
}
