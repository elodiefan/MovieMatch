package interface_adapter.personal_account;

//import use_case.get_reviews.GetReviewsInputBoundary;
//import use_case.customize.CustomizeInputBoundary;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_lists.GetListsController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;
import use_case.get_security_question.GetSecurityQuestionInputBoundary;

/**
 * The controller for the Account Use Case.
 */
public class PersonalAccountController {

    private final GetSecurityQuestionInputBoundary getSecurityQuestionInteractor;
    private final GetListsController getListsController;
    private final ViewManagerModel viewManagerModel;
    private final LogoutViewModel logoutViewModel;
    private final String resetPasswordViewName;
    private final String homePageViewName;
    private final String getListsViewName;
    private final String getReviewsViewName;

    public PersonalAccountController(ViewManagerModel viewManagerModel,
                                     GetSecurityQuestionInputBoundary getSecurityQuestionInteractor,
                                     GetListsController getListsController,
                                     LogoutViewModel logoutViewModel,
                                     String resetPasswordViewName,
                                     String homePageViewName,
                                     String getListsViewName,
                                     String getReviewsViewName) {
        this.viewManagerModel = viewManagerModel;
        this.getSecurityQuestionInteractor = getSecurityQuestionInteractor;
        this.getListsController = getListsController;
        this.logoutViewModel = logoutViewModel;
        this.resetPasswordViewName = resetPasswordViewName;
        this.homePageViewName = homePageViewName;
        this.getListsViewName = getListsViewName;
        this.getReviewsViewName = getReviewsViewName;
    }

    /**
     * Switches to the logout confirmation view.
     *
     * The confirm view needs to know who is logging out, so the username is
     * carried across in the logout state before the view is shown.
     */
    public void switchToLogoutConfirmView(String username) {
        final LogoutState logoutState = logoutViewModel.getState();
        logoutState.setUsername(username);
        logoutViewModel.setState(logoutState);

        viewManagerModel.switchView(logoutViewModel.getViewName());
    }

    /**
     * Executes the reviews view use case.
     */
    public void switchToReviewsView() {
        viewManagerModel.switchView(getReviewsViewName);
    }
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
     */
    public void switchToWatchlistView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchlistUseCase(username, displayName);
    }

    /**
     * Executes the get watch history view use case.
     */
    public void switchToWatchHistoryView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchHistoryUseCase(username, displayName);
    }

    /**
     * Executes the get watch history view use case.
     */
    public void switchToBlockedUsersView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeBlockUsersUseCase(username, displayName);
    }
}
