package interface_adapter.settings;

import use_case.settings.SettingsInteractor;

/**
 * The state for the Settings View.
 */
public class SettingsState {

    private boolean darkMode;
    private int textSize = SettingsInteractor.DEFAULT_TEXT_SIZE;

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
