package interface_adapter.personal_account;

//import use_case.get_watchlist.GetWatchlistInputBoundary;
//import use_case.get_watch_history.GetWatchHistoryInputBoundary;
//import use_case.get_reviews.GetReviewsInputBoundary;
//import use_case.customize.CustomizeInputBoundary;

/**
 * The controller for the Account Use Case.
 */
public class PersonalAccountController {

//    private final AccountInputBoundary accountUseCaseInteractor;

//    private final GetWatchlistInputBoundary getWatchlistInteractor;
//    private final GetWatchHistoryInputBoundary getWatchHistoryInteractor;
//    private final GetReviewsInputBoundary getReviewsInteractor;
//    private final CustomizeInputBoundary customizeInteractor;

    public PersonalAccountController() {

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

    // TODO: switch to homepage view
    public void switchToHomePageView() {

    }
}
