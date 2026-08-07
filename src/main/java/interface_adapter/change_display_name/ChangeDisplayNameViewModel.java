package interface_adapter.change_display_name;

import interface_adapter.ViewModel;

/**
 * View model for the change display name view.
 */
public class ChangeDisplayNameViewModel extends ViewModel<ChangeDisplayNameState> {

    public static final String VIEW_NAME = "change display name";
    public static final String TITLE_LABEL = "Change display name";
    public static final String NEW_DISPLAY_NAME_LABEL = "New display name: ";
    public static final String CONFIRM_BUTTON = "Confirm changes";
    public static final String CANCEL_BUTTON = "Cancel";

    public ChangeDisplayNameViewModel() {
        super("change display name");
        setState(new ChangeDisplayNameState());
    }
}
