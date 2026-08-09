package use_case.settings;

/**
 * The input data for the Change Settings Use Case.
 */
public class SettingsInputData {

    private final boolean darkMode;
    private final int textSize;
    private final boolean allowAdultContent;

    public SettingsInputData(boolean darkMode, int textSize, boolean allowAdultContent) {
        this.darkMode = darkMode;
        this.textSize = textSize;
        this.allowAdultContent = allowAdultContent;
    }

    boolean isDarkMode() {
        return darkMode;
    }

    int getTextSize() {
        return textSize;
    }

    boolean isAllowAdultContent() {
        return allowAdultContent;
    }
}
