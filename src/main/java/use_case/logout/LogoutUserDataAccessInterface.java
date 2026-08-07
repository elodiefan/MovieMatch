package use_case.logout;

/**
 * DAO for the Logout Use Case.
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Returns the username of the curren user of the application.
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     */
    void setCurrentUsername(String username);
}
