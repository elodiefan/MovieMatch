package interface_adapter.home_page;

import interface_adapter.ViewManagerModel;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.get_profile.GetProfileInputData;

/**
 * Controller for the Home Page Use Case.
 */

public class HomePageController {
    private final GetProfileInputBoundary getProfileInteractor;
    private final ViewManagerModel viewManagerModel;
    private final String searchUserViewName;

//    public HomePageController(HomePageInputBoundary homePageUseCaseInteractor) {
//        this.homePageUseCaseInteractor = homePageUseCaseInteractor;
//    }
    public HomePageController(GetProfileInputBoundary getProfileInteractor,
                              ViewManagerModel viewManagerModel,
                              String searchUserViewName) {
        this.getProfileInteractor = getProfileInteractor;
        this.viewManagerModel = viewManagerModel;
        this.searchUserViewName = searchUserViewName;
    }

    /**
     * Switches view to the Search User View.
     * <p>
     * Opening a view is not a use case, it changes no data and has no business
     * rules, so this goes straight to the view manager rather than through an
     * interactor. Same as {@code PersonalAccountController} does.
     */
    public void switchToSearchUserView() {
        viewManagerModel.switchView(searchUserViewName);
    }

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
