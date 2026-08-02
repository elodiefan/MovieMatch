package interface_adapter.account;

import interface_adapter.ViewManagerModel;
import interface_adapter.delete_account.DeleteAccountViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.reset_password.ResetPasswordViewModel;
import use_case.account.AccountOutputBoundary;

/**
 * The Presenter for the Account Use Case.
 */
public class AccountPresenter implements AccountOutputBoundary {

    private final AccountViewModel accountViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ReviewsViewModel reviewsViewModel;
    private final LogoutConfirmViewModel logoutConfirmViewModel;
    private final ResetPasswordViewModel resetPasswordViewModel;
    private final DeleteAccountViewModel deleteAccountViewModel;

    public AccountPresenter(ViewManagerModel viewManagerModel,
                            AccountViewModel accountViewModel,
                            ReviewsViewModel reviewsViewModel,
                            LogoutConfirmViewModel logoutConfirmViewModel,
                            ResetPasswordViewModel resetPasswordViewModel,
                            DeleteAccountViewModel deleteAccountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.accountViewModel = accountViewModel;
        this.reviewsViewModel = reviewsViewModel;
        this.logoutConfirmViewModel = logoutConfirmViewModel;
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.deleteAccountViewModel = deleteAccountViewModel;
    }

    @Override
    public void switchToReviewsView() {
        final ReviewsState reviewsState = reviewsViewModel.getState();
        // gets the current state object from reviewsViewModel and stores it in local variable reviewsState
        reviewsState.setUsername(accountViewModel.getState().getUsername());
        // so that before switching views, it can copy the acc username into reviewsState
        // so if the acc page is showing user "elodie", the reviewsState now also knows about that
        reviewsViewModel.setState(reviewsState);
        // puts the updated reviewsState back into reviewViewModel
        reviewsViewModel.firePropertyChanged();
        // notify reviewsViewModel that the state has changed, so that reviewsView can later refresh
        viewManagerModel.setState(reviewsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToLogoutConfirmView() {
        viewManagerModel.setState(logoutConfirmViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
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
