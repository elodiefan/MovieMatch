package interface_adapter.settings;

import interface_adapter.StateModel;

/**
 * The View Model for the Settings View.
 */
public class SettingsViewModel extends StateModel<SettingsState> {

    public static final String VIEW_NAME = "settings";

    public static final String TITLE_LABEL = "Settings";
    public static final String DARK_MODE_LABEL = "Dark mode";
    public static final String TEXT_SIZE_LABEL = "Text size";
    public static final String ADULT_CONTENT_LABEL = "Show adult recommendations";
    public static final String ADULT_CONTENT_HINT =
            "<html>Off by default. Leave it off and adult titles<br>"
                    + "are never requested.</html>";
    public static final String BACK_BUTTON_LABEL = "Back";

    public static final int MIN_TEXT_SIZE = 10;
    public static final int MAX_TEXT_SIZE = 25;
    public static final int DEFAULT_TEXT_SIZE = 22;

    public SettingsViewModel() {
        super(VIEW_NAME);
        setState(new SettingsState());
    }
}
