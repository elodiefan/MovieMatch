package interface_adapter.change_display_name;

import interface_adapter.ViewManagerModel;
import interface_adapter.personal_account.PersonalAccountState;
import interface_adapter.personal_account.PersonalAccountViewModel;
import use_case.change_display_name.ChangeDisplayNameInputBoundary;
import use_case.change_display_name.ChangeDisplayNameInputData;

/**
 * Controller for the change display name use case.
 */
public class ChangeDisplayNameController {

    private final ChangeDisplayNameInputBoundary changeDisplayNameInteractor;
    private final ViewManagerModel viewManagerModel;
    private final PersonalAccountViewModel personalAccountViewModel;

    public ChangeDisplayNameController(ChangeDisplayNameInputBoundary changeDisplayNameInteractor,
                                       ViewManagerModel viewManagerModel,
                                       PersonalAccountViewModel personalAccountViewModel) {
        this.changeDisplayNameInteractor = changeDisplayNameInteractor;
        this.viewManagerModel = viewManagerModel;
        this.personalAccountViewModel = personalAccountViewModel;
    }

    /**
     * Ask the use case to set a new display name for the user.
     * @param username the account whose display name is being changed
     * @param newDisplayName the new display name
     */
    public void changeDisplayName(String username, String newDisplayName) {
        changeDisplayNameInteractor.changeDisplayName(new ChangeDisplayNameInputData(username, newDisplayName));
    }

    /**
     * Executes the switch to change display name view.
     * @param username the username of the user.
     * @param displayName the display name of the user.
     */
    public void switchToPersonalAccountView(String username, String displayName) {
        final PersonalAccountState personalAccountState = personalAccountViewModel.getState();
        personalAccountState.setUsername(username);
        personalAccountState.setDisplayName(displayName);
        personalAccountViewModel.setState(personalAccountState);
        personalAccountViewModel.firePropertyChanged();
        viewManagerModel.switchView(personalAccountViewModel.getViewName());
    }
}
