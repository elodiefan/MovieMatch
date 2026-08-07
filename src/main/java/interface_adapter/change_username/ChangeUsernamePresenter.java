package interface_adapter.change_username;

import interface_adapter.ViewManagerModel;
import use_case.change_username.ChangeUsernameOutputBoundary;
import use_case.change_username.ChangeUsernameOutputData;

/**
 * Presenter for the change username use case.
 */
public class ChangeUsernamePresenter implements ChangeUsernameOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ChangeUsernameViewModel changeUsernameViewModel;

    public ChangeUsernamePresenter(ViewManagerModel viewManagerModel,
                                   ChangeUsernameViewModel changeUsernameViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.changeUsernameViewModel = changeUsernameViewModel;
    }

    @Override
    public void prepareSuccessView(ChangeUsernameOutputData outputData) {
        final ChangeUsernameState changeUsernameState = changeUsernameViewModel.getState();
        changeUsernameState.setNewUsername(outputData.getNewUsername());
        changeUsernameState.setError("");
        changeUsernameState.setMessage("Username changes saved.");
        changeUsernameViewModel.setState(changeUsernameState);
        changeUsernameViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ChangeUsernameState state = changeUsernameViewModel.getState();
        state.setMessage("");
        state.setError(errorMessage);
        changeUsernameViewModel.setState(state);
        changeUsernameViewModel.firePropertyChanged();
    }
}
