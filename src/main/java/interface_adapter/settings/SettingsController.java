package interface_adapter.settings;

import interface_adapter.ViewManagerModel;
import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInputData;

/**
 * The Controller for the Change Settings Use Case.
 */
public class SettingsController {

    private final SettingsInputBoundary settingsInteractor;
    private final ViewManagerModel viewManagerModel;
    private final String homePageViewName;

    public SettingsController(SettingsInputBoundary settingsInteractor,
                              ViewManagerModel viewManagerModel,
                              String homePageViewName) {
        this.settingsInteractor = settingsInteractor;
        this.viewManagerModel = viewManagerModel;
        this.homePageViewName = homePageViewName;
    }

    /**
     * Applies the display settings the user chose.
     */
    public void execute(boolean darkMode, int textSize) {
        settingsInteractor.execute(new SettingsInputData(darkMode, textSize));
    }

    /**
     * Returns to the home page.
     */
    public void switchToHomePageView() {
        viewManagerModel.switchView(homePageViewName);
    }
}
