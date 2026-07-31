package interface_adapter.home_page;

import use_case.home_page.HomePageInputBoundary;

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
     */
    public void switchToAccountView() {
        homePageUseCaseInteractor.switchToAccountView();
    }
}
