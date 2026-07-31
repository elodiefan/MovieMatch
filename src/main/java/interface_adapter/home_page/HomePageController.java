package interface_adapter.home_page;

import use_case.home_page.HomePageInputBoundary;
import use_case.home_page.HomePageInputData;

/**
 * Controller for the Home Page Use Case.
 */

public class HomePageController {
    private final HomePageInputBoundary homePageUseCaseInteractor;

    public HomePageController(HomePageInputBoundary homePageUseCaseInteractor) {
        this.homePageUseCaseInteractor = homePageUseCaseInteractor;
    }

    /**
     * Swtiches view to Search View.
     */
    public void switchToSearchView() {
        homePageUseCaseInteractor.switchToSearchView();
    }

    /**
     * Switches view to personal Account View.
     * @param username current user's username
     * @param displayName current user's displayName
     */
    public void switchToAccountView(String username, String displayName) {
        final HomePageInputData homePageInputData = new HomePageInputData(username, displayName);
        homePageUseCaseInteractor.switchToAccountView(homePageInputData);
    }
}
