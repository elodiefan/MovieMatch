package interface_adapter.settings;

import interface_adapter.StateModel;
import use_case.settings.SettingsInteractor;

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

    // Taken from the use case rather than repeated, so the slider can never
    // offer a size the interactor would only clamp away again.
    public static final int MIN_TEXT_SIZE = SettingsInteractor.MIN_TEXT_SIZE;
    public static final int MAX_TEXT_SIZE = SettingsInteractor.MAX_TEXT_SIZE;
    public static final int DEFAULT_TEXT_SIZE = SettingsInteractor.DEFAULT_TEXT_SIZE;

    public SettingsViewModel() {
        super(VIEW_NAME);
        setState(new SettingsState());
    }
}
