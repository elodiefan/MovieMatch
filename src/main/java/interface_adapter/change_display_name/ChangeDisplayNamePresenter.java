package interface_adapter.change_display_name;

import interface_adapter.ViewManagerModel;
import use_case.change_display_name.ChangeDisplayNameOutputBoundary;
import use_case.change_display_name.ChangeDisplayNameOutputData;

/**
 * Presenter for the change display name use case.
 */
public class ChangeDisplayNamePresenter implements ChangeDisplayNameOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ChangeDisplayNameViewModel changeDisplayNameViewModel;

    public ChangeDisplayNamePresenter(ViewManagerModel viewManagerModel,
                                      ChangeDisplayNameViewModel changeDisplayNameViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.changeDisplayNameViewModel = changeDisplayNameViewModel;
    }

    @Override
    public void prepareSuccessView(ChangeDisplayNameOutputData outputData) {
        final ChangeDisplayNameState changeDisplayNameState = changeDisplayNameViewModel.getState();
        changeDisplayNameState.setNewDisplayName(outputData.getNewDisplayName());
        changeDisplayNameState.setError("");
        changeDisplayNameState.setMessage("Display name changes saved.");
        changeDisplayNameViewModel.setState(changeDisplayNameState);
        changeDisplayNameViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ChangeDisplayNameState state = changeDisplayNameViewModel.getState();
        state.setMessage("");
        state.setError(errorMessage);
        changeDisplayNameViewModel.setState(state);
        changeDisplayNameViewModel.firePropertyChanged();
    }
}
