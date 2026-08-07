package interface_adapter.settings;

import use_case.settings.SettingsOutputBoundary;
import use_case.settings.SettingsOutputData;

/**
 * The Presenter for the Change Settings Use Case.
 * <p>
 * Changing a setting never leaves the settings screen, so this only writes
 * state. Repainting the application is left to whoever listens to the view
 * model, which keeps Swing out of the interface adapter layer.
 */
public class SettingsPresenter implements SettingsOutputBoundary {

    private final SettingsViewModel settingsViewModel;

    public SettingsPresenter(SettingsViewModel settingsViewModel) {
        this.settingsViewModel = settingsViewModel;
    }

    @Override
    public void prepareSuccessView(SettingsOutputData settingsOutputData) {
        final SettingsState state = settingsViewModel.getState();
        state.setDarkMode(settingsOutputData.isDarkMode());
        state.setTextSize(settingsOutputData.getTextSize());

        settingsViewModel.setState(state);
        settingsViewModel.firePropertyChanged();
    }
}
