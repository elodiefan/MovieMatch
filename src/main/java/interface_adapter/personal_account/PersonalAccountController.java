package interface_adapter.personal_account;

//import use_case.get_watchlist.GetWatchlistInputBoundary;
//import use_case.get_watch_history.GetWatchHistoryInputBoundary;
//import use_case.get_reviews.GetReviewsInputBoundary;
//import use_case.customize.CustomizeInputBoundary;

import interface_adapter.ViewManagerModel;
import use_case.get_security_question.GetSecurityQuestionInputBoundary;

/**
 * The controller for the Account Use Case.
 */
public class PersonalAccountController {

//    private final AccountInputBoundary accountUseCaseInteractor;

//    private final GetWatchlistInputBoundary getWatchlistInteractor;
//    private final GetWatchHistoryInputBoundary getWatchHistoryInteractor;
//    private final GetReviewsInputBoundary getReviewsInteractor;
//    private final CustomizeInputBoundary customizeInteractor;
    private final GetSecurityQuestionInputBoundary getSecurityQuestionInteractor;
    private final ViewManagerModel viewManagerModel;
    private final String resetPasswordViewName = "reset password";
    private final String homePageViewName = "home page";

    public PersonalAccountController(GetSecurityQuestionInputBoundary getSecurityQuestionInteractor, ViewManagerModel viewManagerModel) {
        this.getSecurityQuestionInteractor = getSecurityQuestionInteractor;
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
        viewManagerModel.switchView(resetPasswordViewName);
    }

    /**
     * Executes the delete account view use case.
     */
    public void switchToDeleteAccountView() {
        getSecurityQuestionInteractor.switchToDeleteAccountView();
    }

    /**
     * Switches view to home page.
     */
    public void switchToHomePageView() {
        viewManagerModel.switchView(homePageViewName);
    }
}
