package interface_adapter.account;

import use_case.account.AccountInputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;

/**
 * The controller for the Account Use Case.
 */
public class AccountController {

    private AccountInputBoundary accountUseCaseInteractor;
    private GetWatchlistInputBoundary getWatchlistInteractor;

    public AccountController(AccountInputBoundary accountUseCaseInteractor) {
        this.accountUseCaseInteractor = accountUseCaseInteractor;
    }

    public AccountController(GetWatchlistInputBoundary getWatchlistInteractor) {
        this.getWatchlistInteractor = getWatchlistInteractor;
    }

//    /**
//     * Executes the reviews view use case.
//     */
//    public void switchToReviewsView() {
//        accountUseCaseInteractor.switchToReviewsView();
//    }
//
//    /**
//     * Executes the log out view use case.
//     */
//    public void switchToLogOutConfirmView() {
//        accountUseCaseInteractor.switchToLogOutConfirmView();
//    }

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

    /**
     * Executes the get lists view use case.
     */
    public void switchToGetListsView() {
        getWatchlistInteractor.switchToGetListsView();
    }
}
