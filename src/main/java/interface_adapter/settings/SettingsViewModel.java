package interface_adapter.settings;

import interface_adapter.ViewModel;

/**
 * The View Model for the Settings View.
 */
public class SettingsViewModel extends ViewModel<SettingsState> {

    public static final String VIEW_NAME = "settings";

    public static final String TITLE_LABEL = "Settings";
    public static final String DARK_MODE_LABEL = "Dark mode";
    public static final String TEXT_SIZE_LABEL = "Text size";
    public static final String BACK_BUTTON_LABEL = "Back";

    public SettingsViewModel() {
        super(VIEW_NAME);
        setState(new SettingsState());
    }
}
