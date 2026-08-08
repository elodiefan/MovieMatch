package use_case.settings;

/**
 * The output data for the Change Settings Use Case.
 */
public class SettingsOutputData {

    private final boolean darkMode;
    private final int textSize;

    public SettingsOutputData(boolean darkMode, int textSize) {
        this.darkMode = darkMode;
        this.textSize = textSize;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public int getTextSize() {
        return textSize;
    }
}
