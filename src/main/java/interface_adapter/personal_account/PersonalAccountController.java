package interface_adapter.personal_account;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_display_name.ChangeDisplayNameState;
import interface_adapter.change_display_name.ChangeDisplayNameViewModel;
import interface_adapter.change_username.ChangeUsernameState;
import interface_adapter.change_username.ChangeUsernameViewModel;
import interface_adapter.get_lists.GetListsController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.reset_password.ResetPasswordState;
import interface_adapter.reset_password.ResetPasswordViewModel;
import interface_adapter.user_reviews.UserReviewsState;
import interface_adapter.user_reviews.UserReviewsViewModel;
import use_case.get_security_question.GetSecurityQuestionInputBoundary;

/**
 * The controller for the Account Use Case.
 */
public class PersonalAccountController {

    private final GetSecurityQuestionInputBoundary getSecurityQuestionInteractor;
    private final GetListsController getListsController;
    private final ViewManagerModel viewManagerModel;
    private final ChangeDisplayNameViewModel changeDisplayNameViewModel;
    private final ChangeUsernameViewModel changeUsernameViewModel;
    private final LogoutViewModel logoutViewModel;
    private final ResetPasswordViewModel resetPasswordViewModel;
    private final UserReviewsViewModel userReviewsViewModel;
    private final String homePageViewName;
    private final String getListsViewName;

    public PersonalAccountController(ViewManagerModel viewManagerModel,
                                     GetSecurityQuestionInputBoundary getSecurityQuestionInteractor,
                                     GetListsController getListsController,
                                     ChangeDisplayNameViewModel changeDisplayNameViewModel,
                                     ChangeUsernameViewModel changeUsernameViewModel,
                                     LogoutViewModel logoutViewModel,
                                     ResetPasswordViewModel resetPasswordViewModel,
                                     String homePageViewName,
                                     String getListsViewName,
                                     UserReviewsViewModel userReviewsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getSecurityQuestionInteractor = getSecurityQuestionInteractor;
        this.getListsController = getListsController;
        this.logoutViewModel = logoutViewModel;
        this.changeDisplayNameViewModel = changeDisplayNameViewModel;
        this.changeUsernameViewModel = changeUsernameViewModel;
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.userReviewsViewModel = userReviewsViewModel;
        this.homePageViewName = homePageViewName;
        this.getListsViewName = getListsViewName;
    }

    /**
     * Switches to the logout confirmation view.
     * The confirm view needs to know who is logging out, so the username is
     * carried across in the logout state before the view is shown.
     *
     * @param username the username
     */
    public void switchToLogoutConfirmView(String username) {
        final LogoutState logoutState = logoutViewModel.getState();
        logoutState.setUsername(username);
        logoutViewModel.setState(logoutState);

        viewManagerModel.switchView(logoutViewModel.getViewName());
    }

    /**
     * Executes the reviews view use case.
     * @param username the current username
     */
    public void switchToReviewsView(String username) {
        final UserReviewsState userReviewsState = userReviewsViewModel.getState();
        userReviewsState.setUsername(username);
        userReviewsViewModel.setState(userReviewsState);
        userReviewsViewModel.firePropertyChanged();

        viewManagerModel.switchView(userReviewsViewModel.getViewName());
    }

    /**
     * Executes the reset password view use case.
     * @param username the username whose password is being reset
     */
    public void switchToResetPasswordView(String username) {
        final ResetPasswordState resetPasswordState = resetPasswordViewModel.getState();
        resetPasswordState.setUsername(username);
        resetPasswordState.setNewPassword("");
        resetPasswordState.setConfirmPassword("");
        resetPasswordState.setMessage("");
        resetPasswordState.setError("");
        resetPasswordViewModel.setState(resetPasswordState);
        resetPasswordViewModel.firePropertyChanged();

        viewManagerModel.switchView(resetPasswordViewModel.getViewName());
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
     *
     * @param username the username
     * @param displayName the display name
     */
    public void switchToWatchlistView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchlistUseCase(username, displayName);
    }

    /**
     * Executes the get watch history view use case.
     *
     * @param username the username
     * @param displayName the display name
     */
    public void switchToWatchHistoryView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchHistoryUseCase(username, displayName);
    }

    /**
     * Executes the get watch history view use case.
     *
     * @param username the username
     * @param displayName the display name
     */
    public void switchToBlockedUsersView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeBlockUsersUseCase(username, displayName);
    }

    /**
     * Executes the switch to change display name view.
     * @param username the username of the user.
     * @param displayName the display name of the user.
     */
    public void switchToChangeDisplayNameView(String username, String displayName) {
        final ChangeDisplayNameState changeDisplayNameState = new ChangeDisplayNameState();
        changeDisplayNameState.setUsername(username);
        changeDisplayNameState.setOldDisplayName(displayName);
        changeDisplayNameViewModel.setState(changeDisplayNameState);
        changeDisplayNameViewModel.firePropertyChanged();
        viewManagerModel.switchView(changeDisplayNameViewModel.getViewName());
    }

    /**
     * Executes the switch to change username view.
     * @param username username of the given user.
     * @param displayName display name of the given user.
     */
    public void switchToChangeUsernameView(String username, String displayName) {
        final ChangeUsernameState changeUsernameState = new ChangeUsernameState();
        changeUsernameState.setUsername(username);
        changeUsernameState.setDisplayName(displayName);
        changeUsernameViewModel.setState(changeUsernameState);
        changeUsernameViewModel.firePropertyChanged();
        viewManagerModel.switchView(changeUsernameViewModel.getViewName());
    }
}
