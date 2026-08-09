package interface_adapter.settings;

import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInputData;

/**
 * The Controller for the Change Settings Use Case.
 */
public class SettingsController {

    private final SettingsInputBoundary settingsInteractor;

    public SettingsController(SettingsInputBoundary settingsInteractor) {
        this.settingsInteractor = settingsInteractor;
    }

    /**
     * Applies the display settings the user chose.
     *
     * @param darkMode the dark mode
     * @param textSize the text size
     * @param allowAdultContent whether adult titles may be recommended
     */
    public void execute(boolean darkMode, int textSize, boolean allowAdultContent) {
        settingsInteractor.execute(
                new SettingsInputData(darkMode, textSize, allowAdultContent));
    }
}
