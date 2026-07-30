package interface_adapter.account;

import interface_adapter.ViewManagerModel;
import use_case.account.AccountOutputBoundary;
import use_case.account.AccountOutputData;

/**
 * The Presenter for the Account Use Case.
 */
public class AccountPresenter implements AccountOutputBoundary {

    private final AccountViewModel accountViewModel;
    private final ViewManagerModel viewManagerModel;

    public AccountPresenter(ViewManagerModel viewManagerModel,
                          AccountViewModel accountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.accountViewModel = accountViewModel;
    }

    // idk if i need this one tbh
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
}
