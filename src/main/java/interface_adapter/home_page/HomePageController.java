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

    public void switchToSearchView() {
        homePageUseCaseInteractor.switchToSearchView();
    }

    public void switchToAccountView() {
        homePageUseCaseInteractor.switchToAccountView();
    }
}
