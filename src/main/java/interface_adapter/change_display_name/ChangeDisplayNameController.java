package interface_adapter.change_display_name;

import use_case.change_display_name.ChangeDisplayNameInputBoundary;
import use_case.change_display_name.ChangeDisplayNameInputData;

/**
 * Controller for the change display name use case.
 */
public class ChangeDisplayNameController {

    private final ChangeDisplayNameInputBoundary changeDisplayNameInteractor;

    public ChangeDisplayNameController(ChangeDisplayNameInputBoundary changeDisplayNameInteractor) {
        this.changeDisplayNameInteractor = changeDisplayNameInteractor;
    }

    /**
     * Ask the use case to set a new display name for the user.
     * @param username the account whose display name is being changed
     * @param newDisplayName the new display name
     */
    public void changeDisplayName(String username, String newDisplayName) {
        changeDisplayNameInteractor.changeDisplayName(new ChangeDisplayNameInputData(username, newDisplayName));
    }
}
