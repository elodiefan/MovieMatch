package use_case.settings;

/**
 * The output boundary for the Change Settings Use Case.
 */
public interface SettingsOutputBoundary {

    /**
     * Presents the settings that were actually applied.
     *
     * @param settingsOutputData the settings output data
     */
    void prepareSuccessView(SettingsOutputData settingsOutputData);
}
