package use_case.settings;

/**
 * The Change Settings Interactor.
 */
public class SettingsInteractor implements SettingsInputBoundary {

    /**
     * Smallest readable text size.
     */
    public static final int MIN_TEXT_SIZE = 10;

    /**
     * Largest size the layouts still hold together at.
     */
    public static final int MAX_TEXT_SIZE = 36;

    /**
     * The size the application starts at.
     */
    public static final int DEFAULT_TEXT_SIZE = 22;

    private final SettingsOutputBoundary settingsPresenter;
    private final ContentPreferenceDataAccessInterface contentPreferences;

    public SettingsInteractor(SettingsOutputBoundary settingsPresenter,
                              ContentPreferenceDataAccessInterface contentPreferences) {
        this.settingsPresenter = settingsPresenter;
        this.contentPreferences = contentPreferences;
    }

    @Override
    public void execute(SettingsInputData settingsInputData) {
        final int textSize = clamp(settingsInputData.getTextSize());
        final boolean allowAdultContent = settingsInputData.isAllowAdultContent();

        // Recorded before the screen is told, so that anything asking for
        // recommendations straight afterwards already sees the new choice.
        contentPreferences.setAdultContentAllowed(allowAdultContent);

        settingsPresenter.prepareSuccessView(new SettingsOutputData(
                settingsInputData.isDarkMode(), textSize, allowAdultContent));
    }

    /**
     * Holds a text size inside the readable range.
     *
     * @param textSize the text size
     * @return the clamp
     */
    private int clamp(int textSize) {
        int result = textSize;
        if (result < MIN_TEXT_SIZE) {
            result = MIN_TEXT_SIZE;
        }
        else if (result > MAX_TEXT_SIZE) {
            result = MAX_TEXT_SIZE;
        }
        return result;
    }
}
