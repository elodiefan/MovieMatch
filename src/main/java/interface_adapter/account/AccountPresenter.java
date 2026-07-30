package interface_adapter.account;

import interface_adapter.ViewManagerModel;
import interface_adapter.delete_account.DeleteAccountViewModel;
import interface_adapter.reset_password.ResetPasswordViewModel;
import use_case.account.AccountOutputBoundary;
import use_case.account.AccountOutputData;

/**
 * The Presenter for the Account Use Case.
 */
public class AccountPresenter implements AccountOutputBoundary {

    private final AccountViewModel accountViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ResetPasswordViewModel resetPasswordViewModel;
    private final DeleteAccountViewModel deleteAccountViewModel;

    public AccountPresenter(ViewManagerModel viewManagerModel,
                          AccountViewModel accountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.accountViewModel = accountViewModel;
    }

    @Override
    public void prepareSuccessView(AccountOutputData response) {
        // On success, switch to the logged in view.

        final AccountState accountState = accountViewModel.getState();
        accountState.setUsername(response.getUsername());
        this.accountViewModel.setState(accountState);
        this.accountViewModel.firePropertyChanged();

        this.viewManagerModel.setState(accountViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final AccountState accountState = accountViewModel.getState();
        accountState.setAccountError(error);
        accountViewModel.firePropertyChanged();
    }

    @Override
    public void switchToResetPasswordView() {
        viewManagerModel.setState(resetPasswordViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToDeleteAccountView() {
        viewManagerModel.setState(deleteAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
