package interface_adapter.home_page;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search_user.SearchUserViewModel;
import interface_adapter.settings.SettingsViewModel;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.get_profile.GetProfileInputData;

/**
 * Controller for the Home Page Use Case.
 */

public class HomePageController {
    private final GetProfileInputBoundary getProfileInteractor;
    private final ViewManagerModel viewManagerModel;

    public HomePageController(
        GetProfileInputBoundary getProfileInteractor,
        ViewManagerModel viewManagerModel) {
        this.getProfileInteractor = getProfileInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Swtiches view to Search View.
     */
    public void switchToSearchView() {
        viewManagerModel.setState(SearchViewModel.VIEW_NAME);
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
        viewManagerModel.switchView(SettingsViewModel.VIEW_NAME);
    }

    /**
     * Switches view to the Search User View.
     */
    public void switchToSearchUserView() {
        viewManagerModel.switchView(SearchUserViewModel.VIEW_NAME);
    }

    /**
     * Gets personal profile view
     */
    public void switchToPersonalAccountView(String username, String displayName) {
        final GetProfileInputData getProfileInputData = new GetProfileInputData(username, displayName);
        getProfileInteractor.execute(getProfileInputData);
    }
}
