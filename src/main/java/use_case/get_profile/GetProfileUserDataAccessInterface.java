package use_case.get_profile;

public interface GetProfileUserDataAccessInterface {

    /**
     * Gets the username of the current user.
     */
    String getCurrentUsername();

    /**
     * Gets the diplay name of the current user.
     */
    String getDisplayName(String username);
}
