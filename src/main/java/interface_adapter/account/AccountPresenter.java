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
    private final LogOutConfirmViewModel logOutConfirmViewModel;
    private final ResetPasswordViewModel resetPasswordViewModel;
    private final DeleteAccountViewModel deleteAccountViewModel;

    public AccountPresenter(ViewManagerModel viewManagerModel,
                            AccountViewModel accountViewModel,
                            ReviewsViewModel reviewsViewModel,
                            LogOutConfirmViewModel logOutConfirmViewModel,
                            ResetPasswordViewModel resetPasswordViewModel,
                            DeleteAccountViewModel deleteAccountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.accountViewModel = accountViewModel;
        this.reviewsViewModel = reviewsViewModel;
        this.logOutConfirmViewModel = logOutConfirmViewModel;
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.deleteAccountViewModel = deleteAccountViewModel;
    }

    @Override
    public void switchToReviewsView() {
        viewManagerModel.setState(reviewsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToLogOutConfirmView() {
        viewManagerModel.setState(logOutConfirmViewModel.getViewName());
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
