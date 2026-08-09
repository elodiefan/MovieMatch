package interface_adapter.personal_account;

import interface_adapter.ViewManagerModel;
import interface_adapter.delete_account.DeleteAccountState;
import interface_adapter.delete_account.DeleteAccountViewModel;
import interface_adapter.reset_password.ResetPasswordViewModel;
import use_case.get_security_question.GetSecurityQuestionOutputBoundary;
import use_case.get_security_question.GetSecurityQuestionOutputData;

/**
 * The Presenter for the Account Use Case.
 */
public class PersonalAccountPresenter implements GetSecurityQuestionOutputBoundary {

    private final PersonalAccountViewModel accountViewModel;
    private final ViewManagerModel viewManagerModel;
    // private final ReviewsViewModel reviewsViewModel;
    // private final LogOutConfirmViewModel logOutConfirmViewModel;
    private final ResetPasswordViewModel resetPasswordViewModel;
    private final DeleteAccountViewModel deleteAccountViewModel;

    public PersonalAccountPresenter(ViewManagerModel viewManagerModel,
                                    PersonalAccountViewModel accountViewModel,
                                    // ReviewsViewModel reviewsViewModel,
                                    // LogOutConfirmViewModel logOutConfirmViewModel,
                                    ResetPasswordViewModel resetPasswordViewModel,
                                    DeleteAccountViewModel deleteAccountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.accountViewModel = accountViewModel;
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.deleteAccountViewModel = deleteAccountViewModel;
    }

    @Override
    public void switchToDeleteAccountView(GetSecurityQuestionOutputData response) {
        final DeleteAccountState deleteAccountState = deleteAccountViewModel.getState();
        deleteAccountState.setUsername(response.getUsername());
        deleteAccountState.setSecurityQuestion(response.getSecurityQuestion());
        deleteAccountState.setDeleteAccountError(null);
        deleteAccountViewModel.setState(deleteAccountState);
        deleteAccountViewModel.firePropertyChanged();

        viewManagerModel.setState(deleteAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
