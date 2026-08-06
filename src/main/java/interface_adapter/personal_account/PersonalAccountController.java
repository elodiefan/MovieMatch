package interface_adapter.personal_account;

//import use_case.get_reviews.GetReviewsInputBoundary;
//import use_case.customize.CustomizeInputBoundary;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_lists.GetListsController;
import use_case.get_security_question.GetSecurityQuestionInputBoundary;

/**
 * The controller for the Account Use Case.
 */
public class PersonalAccountController {

    private final GetSecurityQuestionInputBoundary getSecurityQuestionInteractor;
    private final GetListsController getListsController;
    private final ViewManagerModel viewManagerModel;
    private final String resetPasswordViewName;
    private final String homePageViewName;
    private final String getListsViewName;

    public PersonalAccountController(ViewManagerModel viewManagerModel,
                                     GetSecurityQuestionInputBoundary getSecurityQuestionInteractor,
                                     GetListsController getListsController,
                                     String resetPasswordViewName,
                                     String homePageViewName,
                                     String getListsViewName) {
        this.viewManagerModel = viewManagerModel;
        this.getSecurityQuestionInteractor = getSecurityQuestionInteractor;
        this.getListsController = getListsController;
        this.resetPasswordViewName = resetPasswordViewName;
        this.homePageViewName = homePageViewName;
        this.getListsViewName = getListsViewName;
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

    /**
     * Executes the get watchlist view use case.
     * @param username the username of the user.
     * @param displayName the display name of the user.
     */
    public void switchToWatchlistView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchlistUseCase(username, displayName);
    }

    /**
     * Executes the get watch history view use case.
     * @param username the username of the user.
     * @param displayName the display name of the user.
     */
    public void switchToWatchHistoryView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchHistoryUseCase(username, displayName);
    }

    /**
     * Executes the get watch history view use case.
     * @param username the username of the user.
     * @param displayName the display name of the user.
     */
    public void switchToBlockedUsersView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeBlockUsersUseCase(username, displayName);
    }
}
