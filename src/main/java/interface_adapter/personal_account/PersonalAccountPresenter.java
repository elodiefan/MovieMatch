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
      //  this.reviewsViewModel = reviewsViewModel;
      //  this.logOutConfirmViewModel = logOutConfirmViewModel;
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.deleteAccountViewModel = deleteAccountViewModel;
    }

//    @Override
//    public void switchToReviewsView() {
//        final ReviewsState reviewsState = reviewsViewModel.getState();
//        // gets the current state object from reviewsViewModel and stores it in local variable reviewsState
//        reviewsState.setUsername(accountViewModel.getState().getUsername());
//        // so that before switching views, it can copy the acc username into reviewsState
//        // so if the acc page is showing user "elodie", the reviewsState now also knows about that
//        reviewsViewModel.setState(reviewsState);
//        // puts the updated reviewsState back into reviewViewModel
//        reviewsViewModel.firePropertyChanged();
//        // notify reviewsViewModel that the state has changed, so that reviewsView can later refresh
//        viewManagerModel.setState(reviewsViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }

//    @Override
//    public void switchToLogoutConfirmView() {
//        viewManagerModel.setState(logoutConfirmViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }

//    @Override
//    public void switchToResetPasswordView() {
//        viewManagerModel.setState(resetPasswordViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }

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
