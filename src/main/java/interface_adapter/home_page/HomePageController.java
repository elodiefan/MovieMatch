package interface_adapter.home_page;

import use_case.home_page.HomePageInputBoundary;
import use_case.home_page.HomePageInputData;

/**
 * Controller for the Home Page Use Case.
 */

public class HomePageController {
    private final HomePageInputBoundary homePageUseCaseInteractor;

    public DeleteAccountController(HomePageInputBoundary homePageUseCaseInteractor) {
        this.homePageUseCaseInteractor = homePageUseCaseInteractor;
    }

    /**
     * Executes the Delete Account Use Case.
     * @param username the username of the user logged in
     * @param displayName the display name of the user logged in
     * @param password the password of the user logged in
     * @param securityQuestion the security question for the user's account
     * @param securityAnswer the security answer for the user's account
     */
    public void execute(String username, String displayName, String password, String securityQuestion, String securityAnswer) {
        final HomePageInputData homePageInputData = new HomePageInputData(username, displayName, password, securityQuestion, securityAnswer);
        homePageUseCaseInteractor.execute(homePageInputData);
    }

    public void switchTosearchView() {
        homePageUseCaseInteractor.switchToSearchView();
    }

    public void switchToAccountView() {
        homePageUseCaseInteractor.switchToAccountView();
    }

}