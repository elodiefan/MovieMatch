package interface_adapter.account;

import use_case.account.AccountInputBoundary;

/**
 * The controller for the Account Use Case.
 */
public class AccountController {

    private final AccountInputBoundary accountUseCaseInteractor;

    public AccountController(AccountInputBoundary accountUseCaseInteractor) {
        this.accountUseCaseInteractor = accountUseCaseInteractor;
    }

    /**
     * Executes the reviews view use case.
     */
    public void switchToReviewsView() {
        accountUseCaseInteractor.switchToReviewsView();
    }

    /**
     * Executes the log in view use case.
     */
    public void switchToLoginView() {
        accountUseCaseInteractor.switchToLoginView();
    }

    /**
     * Executes the reset password view use case.
     */
    public void switchToResetPasswordView() {
        accountUseCaseInteractor.switchToResetPasswordView();
    }

    /**
     * Executes the delete account view use case.
     */
    public void switchToDeleteAccountView() {
        accountUseCaseInteractor.switchToDeleteAccountView();
    }
}
