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
    private final String searchViewName;
    private final String searchUserViewName;
    private final String settingsViewName;

    public HomePageController(
        GetProfileInputBoundary getProfileInteractor,
        ViewManagerModel viewManagerModel,
        String searchViewName,
        String searchUserViewName,
        String settingsViewName) {
        this.getProfileInteractor = getProfileInteractor;
        this.viewManagerModel = viewManagerModel;
        this.searchViewName = searchViewName;
        this.searchUserViewName = searchUserViewName;
        this.settingsViewName = settingsViewName;
    }

    /**
     * Swtiches view to Search View.
     */
    public void switchToSearchView() {
        viewManagerModel.setState(searchViewName);
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Switches view to the Search User View.
     *
     * Opening a view is not a use case, it changes no data and has no business
     * rules, so this goes straight to the view manager rather than through an
     * interactor. Same as PersonalAccountController does.
     */
    public void switchToSettingsView() {
        viewManagerModel.switchView(settingsViewName);
    }

    /**
     * Switches view to the Search User View.
     */
    public void switchToSearchUserView() {
        viewManagerModel.switchView(searchUserViewName);
    }

    /**
     * Gets personal profile view
     */
    public void switchToPersonalAccountView(String username, String displayName) {
        final GetProfileInputData getProfileInputData = new GetProfileInputData(username, displayName);
        getProfileInteractor.execute(getProfileInputData);
    }
}
