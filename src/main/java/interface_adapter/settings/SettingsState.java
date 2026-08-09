package interface_adapter.settings;

/**
 * The state for the Settings View.
 */
public class SettingsState {

    private boolean darkMode;
    private int textSize = SettingsViewModel.DEFAULT_TEXT_SIZE;

    /**
     * Off unless the user asks for it.
     */
    private boolean allowAdultContent;

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

    public boolean isAllowAdultContent() {
        return allowAdultContent;
    }

    public void setAllowAdultContent(boolean allowAdultContent) {
        this.allowAdultContent = allowAdultContent;
    }
}
