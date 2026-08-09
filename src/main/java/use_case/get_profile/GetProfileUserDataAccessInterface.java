package use_case.get_profile;

public interface GetProfileUserDataAccessInterface {

    /**
     * Gets the username of the current user.
     * @return the current user's username
     */
    String getCurrentUsername();

    /**
     * Gets the diplay name of the current user.
     * @param username the username of the user
     * @return the current user's display name
     */
    String getDisplayName(String username);

    /**
     * Gets whether current user can message other user or not.
     * @param username username to look for
     * @return whether username blocked by current user
     */
    boolean canMessage(String username);
}
