package interface_adapter.change_username;

import interface_adapter.ViewManagerModel;
import interface_adapter.personal_account.PersonalAccountState;
import interface_adapter.personal_account.PersonalAccountViewModel;
import use_case.change_username.ChangeUsernameInputBoundary;
import use_case.change_username.ChangeUsernameInputData;

/**
 * Controller for the change username use case.
 */
public class ChangeUsernameController {

    private final ChangeUsernameInputBoundary changeUsernameInteractor;
    private final ViewManagerModel viewManagerModel;
    private final PersonalAccountViewModel personalAccountViewModel;

    public ChangeUsernameController(ChangeUsernameInputBoundary changeUsernameInteractor,
                                    ViewManagerModel viewManagerModel,
                                    PersonalAccountViewModel personalAccountViewModel) {
        this.changeUsernameInteractor = changeUsernameInteractor;
        this.viewManagerModel = viewManagerModel;
        this.personalAccountViewModel = personalAccountViewModel;
    }

    /**
     * Ask the use case to set a new username for the user.
     * @param username the account whose username is being changed
     * @param newUsername the new username
     * @param displayName the display name
     */
    public void changeUsername(String username, String newUsername, String displayName) {
        changeUsernameInteractor.changeUsername(new ChangeUsernameInputData(username, newUsername, displayName));
    }

    /**
     * Executes the switch to change username view.
     * @param username the username of the user.
     * @param newUsername the new username name of the user.
     */
    public void switchToPersonalAccountView(String username, String newUsername) {
        final PersonalAccountState personalAccountState = personalAccountViewModel.getState();
        personalAccountState.setUsername(newUsername);
        personalAccountViewModel.setState(personalAccountState);
        personalAccountViewModel.firePropertyChanged();
        viewManagerModel.switchView(personalAccountViewModel.getViewName());
    }
}
