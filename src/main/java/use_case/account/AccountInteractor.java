package use_case.account;

import use_case.home_page.HomePageInputData;
import use_case.home_page.HomePageOutputData;

/**
 * The Account Interactor.
 */
public class AccountInteractor implements AccountInputBoundary {
    private final AccountUserDataAccessInterface userDataAccessObject;
    private final AccountOutputBoundary accountPresenter;

    public AccountInteractor(AccountUserDataAccessInterface userDataAccessInterface,
                           AccountOutputBoundary accountOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.accountPresenter = accountOutputBoundary;
    }

//    /**
//     * Switches from account view to reviews view.
//     */
//    @Override
//    public void switchToReviewsView() {
//        accountPresenter.switchToReviewsView();
//    }
//
//    /**
//     * Switches from account view to reset password view.
//     */
//    @Override
//    public void switchToLogOutConfirmView() {
//        accountPresenter.switchToLogOutConfirmView();
//    }

    /**
     * Switches from account view to reset password view.
     */
    @Override
    public void switchToResetPasswordView() {
        accountPresenter.switchToResetPasswordView();
    }

    /**
     * Switches from account view to delete account view.
     */
    @Override
    public void switchToDeleteAccountView() {
        final String username = userDataAccessObject.getCurrentUsername();
        final String secuirtyQuestion = userDataAccessObject.getSecurityQuestion();
        final AccountOutputData accountOutputData = new AccountOutputData(username, secuirtyQuestion);
        accountPresenter.switchToDeleteAccountView(accountOutputData);
    }

}
