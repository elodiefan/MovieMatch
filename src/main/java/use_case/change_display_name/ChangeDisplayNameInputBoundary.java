package use_case.change_display_name;

/**
 * Input boundary for the change display name use case.
 */
public interface ChangeDisplayNameInputBoundary {

    /**
     * Sets a new display name for the given user.
     * @param inputData the username plus the new display name.
     */
    void changeDisplayName(ChangeDisplayNameInputData inputData);
}
