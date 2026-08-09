package use_case.settings;

/**
 * The output data for the Change Settings Use Case.
 */
public class SettingsOutputData {

    private final boolean darkMode;
    private final int textSize;
    private final boolean allowAdultContent;

    public SettingsOutputData(boolean darkMode, int textSize, boolean allowAdultContent) {
        this.darkMode = darkMode;
        this.textSize = textSize;
        this.allowAdultContent = allowAdultContent;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public int getTextSize() {
        return textSize;
    }

    public boolean isAllowAdultContent() {
        return allowAdultContent;
    }
}
