package interface_adapter.change_display_name;

import interface_adapter.StateModel;

/**
 * View model for the change display name view.
 */
public class ChangeDisplayNameViewModel extends StateModel<ChangeDisplayNameState> {

    public static final String VIEW_NAME = "change display name";
    public static final String TITLE_LABEL = "Change display name";
    public static final String NEW_DISPLAY_NAME_LABEL = "New display name: ";
    public static final String CONFIRM_BUTTON = "Confirm changes";
    public static final String BACK_BUTTON = "Back";
    public static final int COLUMN_SIZE = 15;

    public ChangeDisplayNameViewModel() {
        super("change display name");
        setState(new ChangeDisplayNameState());
    }
}
