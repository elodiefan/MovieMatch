package use_case.settings;

/**
 * The input boundary for the Change Settings Use Case.
 */
public interface SettingsInputBoundary {

    /**
     * Applies the display settings the user asked for.
     *
     * @param settingsInputData the settings input data
     */
    void execute(SettingsInputData settingsInputData);
}
