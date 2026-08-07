package interface_adapter.change_display_name;

import interface_adapter.ViewManagerModel;
import use_case.change_display_name.ChangeDisplayNameOutputBoundary;
import use_case.change_display_name.ChangeDisplayNameOutputData;

/**
 * Presenter for the change display name  use case.
 */
public class ChangeDisplayNamePresenter implements ChangeDisplayNameOutputBoundary, ChangeDisplayNameCompletedHandler {

    private final ViewManagerModel viewManagerModel;
    private final ChangeDisplayNameViewModel changeDisplayNameViewModel;
    private final ChangeDisplayNameCompletedHandler changeDisplayNameCompletedHandler;

    public ChangeDisplayNamePresenter(ViewManagerModel viewManagerModel,
                                      ChangeDisplayNameViewModel changeDisplayNameViewModel,
                                      ChangeDisplayNameCompletedHandler changeDisplayNameCompletedHandler) {
        this.viewManagerModel = viewManagerModel;
        this.changeDisplayNameViewModel = changeDisplayNameViewModel;
        this.changeDisplayNameCompletedHandler = changeDisplayNameCompletedHandler;
    }

    @Override
    public void prepareSuccessView(ChangeDisplayNameOutputData outputData) {
        // Clear the password form so the typed values do not linger.
        final ChangeDisplayNameState state = changeDisplayNameViewModel.getState();
        state.setError("");
        state.setMessage("");
        state.setNewDisplayName("");
        changeDisplayNameViewModel.setState(state);
        changeDisplayNameViewModel.firePropertyChanged();
        changeDisplayNameCompletedHandler.changeDisplayNameCompleted(outputData.getUsername());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Stay on this screen: the user needs to fix the input.
        final ChangeDisplayNameState state = changeDisplayNameViewModel.getState();
        state.setMessage("");
        state.setError(errorMessage);
        changeDisplayNameViewModel.setState(state);
        changeDisplayNameViewModel.firePropertyChanged();
    }

    /**
     * The display name has been changed.
     * @param username the account whose password was changed
     */
    @Override
    public void changeDisplayNameCompleted(String username) {
        final ChangeDisplayNameState changeDisplayNameState = changeDisplayNameViewModel.getState();
        changeDisplayNameState.setUsername("");
        changeDisplayNameState.setError("");
        changeDisplayNameState.setMessage("Display name changes saved.");
        changeDisplayNameViewModel.setState(changeDisplayNameState);
        changeDisplayNameViewModel.firePropertyChanged();

        viewManagerModel.setState(changeDisplayNameViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
