package use_case.get_profile;

/**
 * The output boundary for the get profile use case.
 */

public interface GetProfileOutputBoundary {
    /**
     * Switches to the Personal Account View.
     * @param response the get profile output data
     */
    void switchToPersonalAccountView(GetProfileOutputData response);

    /**
     * Switches to the Other Account View.
     * @param response the get profile output data
     */
    void switchToOtherAccountView(GetProfileOutputData response);
}
