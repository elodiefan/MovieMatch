package use_case.settings;

/**
 * The Change Settings Interactor.
 *
 * The rule this use case owns is the range of text sizes the application
 * supports. A slider happens to enforce it today, but the range belongs to the
 * application rather than to any one widget, so a value arriving from anywhere
 * is clamped here.
 */
public class SettingsInteractor implements SettingsInputBoundary {

    /** Smallest readable text size. */
    public static final int MIN_TEXT_SIZE = 10;

    /** Largest size the layouts still hold together at. */
    public static final int MAX_TEXT_SIZE = 26;

    /** The size the application starts at. */
    public static final int DEFAULT_TEXT_SIZE = 14;

    private final SettingsOutputBoundary settingsPresenter;

    public SettingsInteractor(SettingsOutputBoundary settingsPresenter) {
        this.settingsPresenter = settingsPresenter;
    }

    @Override
    public void execute(SettingsInputData settingsInputData) {
        final int textSize = clamp(settingsInputData.getTextSize());
        settingsPresenter.prepareSuccessView(
                new SettingsOutputData(settingsInputData.isDarkMode(), textSize));
    }

    /**
     * Holds a text size inside the readable range.
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
