package interface_adapter.change_username;

import interface_adapter.StateModel;

/**
 * View model for the change username view.
 */
public class ChangeUsernameViewModel extends StateModel<ChangeUsernameState> {

    public static final String VIEW_NAME = "change username";
    public static final String TITLE_LABEL = "Change username";
    public static final String NEW_USERNAME_LABEL = "New username: ";
    public static final String CONFIRM_BUTTON = "Confirm changes";
    public static final String BACK_BUTTON = "Back";
    public static final int COLUMN_SIZE = 15;

    public ChangeUsernameViewModel() {
        super("change username");
        setState(new ChangeUsernameState());
    }
}
