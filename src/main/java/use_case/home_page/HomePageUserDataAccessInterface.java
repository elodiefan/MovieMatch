package use_case.home_page;

/**
 * Data access interface for the Home Page Use Case.
 */

public interface HomePageUserDataAccessInterface {

    /**
     * Gets the diplay name of the current user.
     * @return the current user's display name
     */
    String getDisplayName();
}
