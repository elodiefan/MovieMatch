package interface_adapter.home_page;

import use_case.get_profile.GetProfileInputBoundary;
import use_case.get_profile.GetProfileInputData;

/**
 * Controller for the Home Page Use Case.
 */

public class HomePageController {
    private final GetProfileInputBoundary getProfileInteractor;

//    public HomePageController(HomePageInputBoundary homePageUseCaseInteractor) {
//        this.homePageUseCaseInteractor = homePageUseCaseInteractor;
//    }
    public HomePageController(GetProfileInputBoundary getProfileInteractor) {
        this.getProfileInteractor = getProfileInteractor;
    }

//    /**
//     * Swtiches view to Search View.
//     */
//    public void switchToSearchView() {
//
//    }

    /**
     * Gets personal profile view
     * @param username the current username of profile to be viewed
     * @param displayName the display name of profile to be viewed
     */
    public void switchToPersonalAccountView(String username, String displayName) {
        final GetProfileInputData getProfileInputData = new GetProfileInputData(username, displayName);
        getProfileInteractor.execute(getProfileInputData);
    }
}
