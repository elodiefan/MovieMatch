package interface_adapter.settings;

import use_case.settings.SettingsOutputBoundary;
import use_case.settings.SettingsOutputData;

/**
 * The Presenter for the Change Settings Use Case.
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
        state.setAllowAdultContent(settingsOutputData.isAllowAdultContent());

        settingsViewModel.setState(state);
        settingsViewModel.firePropertyChanged();
    }
}
