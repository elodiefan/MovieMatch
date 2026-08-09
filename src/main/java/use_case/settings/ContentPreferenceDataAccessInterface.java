package use_case.settings;

/**
 * Where the settings use case records what kind of content the user wants.
 */
public interface ContentPreferenceDataAccessInterface {

    /**
     * Records whether adult titles may be offered to the user.
     *
     * @param allowed whether adult titles are wanted
     */
    void setAdultContentAllowed(boolean allowed);
}
