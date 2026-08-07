package use_case.get_profile;

/**
 * The output boundary for the get profile use case.
 */

public interface GetProfileOutputBoundary {
    /**
     * Switches to the Personal Account View.
     */
    void switchToPersonalAccountView(GetProfileOutputData response);

    /**
     * Switches to the Other Account View.
     */
    void switchToOtherAccountView(GetProfileOutputData response);
}
