package interface_adapter.delete_account;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import use_case.delete_account.DeleteAccountOutputBoundary;
import use_case.delete_account.DeleteAccountOutputData;

/**
 * The Presenter for the Delete Account Use Case.
 */

public class DeleteAccountPresenter implements DeleteAccountOutputBoundary {

    private DeleteAccountViewModel deleteAccountViewModel;
    private ViewManagerModel viewManagerModel;
    private SignupViewModel signupViewModel;

    public DeleteAccountPresenter(ViewManagerModel viewManagerModel,
                                  DeleteAccountViewModel deleteAccountViewModel,
                                  SignupViewModel signupViewModel) {
        this.deleteAccountViewModel = deleteAccountViewModel;
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteAccountOutputData response) {
        final DeleteAccountState deleteAccountState = deleteAccountViewModel.getState();
        deleteAccountState.setUsername("");
        deleteAccountViewModel.setState(deleteAccountState);
        deleteAccountViewModel.firePropertyChanged();

        final SignupState signupState = signupViewModel.getState();
        signupState.setUsername("");
        signupState.setPassword("");
        signupViewModel.setState(signupState);
        signupViewModel.firePropertyChanged();

        // This code tells the View Manager to switch to the Signup View.
        this.viewManagerModel.setState(signupViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final DeleteAccountState deleteAccountState = deleteAccountViewModel.getState();
        deleteAccountState.setDeleteAccountError(error);
        deleteAccountViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
