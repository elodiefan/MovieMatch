package use_case.settings;

/** The Change Settings Interactor. */
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

    /** Holds a text size inside the readable range. */
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
