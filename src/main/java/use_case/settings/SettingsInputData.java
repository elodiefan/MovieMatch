package use_case.settings;

/** The input data for the Change Settings Use Case. */
public class SettingsInputData {

    private final boolean darkMode;
    private final int textSize;

    public SettingsInputData(boolean darkMode, int textSize) {
        this.darkMode = darkMode;
        this.textSize = textSize;
    }

    boolean isDarkMode() {
        return darkMode;
    }

    int getTextSize() {
        return textSize;
    }
}
