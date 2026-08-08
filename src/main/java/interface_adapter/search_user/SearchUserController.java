package interface_adapter.search_user;

import interface_adapter.ViewManagerModel;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.get_profile.GetProfileInputData;
import use_case.search_user.SearchUserInputBoundary;
import use_case.search_user.SearchUserInputData;

/**
 * The controller for the Search User Use Case.
 * <p>
 * Opening a result reuses the Get Profile use case, which already decides
 * between the personal and other-account views, so search does not need
 * navigation logic of its own.
 */
public class SearchUserController {

    private final SearchUserInputBoundary searchUserInteractor;
    private final GetProfileInputBoundary getProfileInteractor;
    private final ViewManagerModel viewManagerModel;
    private final String homePageViewName;

    public SearchUserController(SearchUserInputBoundary searchUserInteractor,
                                GetProfileInputBoundary getProfileInteractor,
                                ViewManagerModel viewManagerModel,
                                String homePageViewName) {
        this.searchUserInteractor = searchUserInteractor;
        this.getProfileInteractor = getProfileInteractor;
        this.viewManagerModel = viewManagerModel;
        this.homePageViewName = homePageViewName;
    }

    /**
     * Executes the Search User Use Case.
     * @param keyword the keyword entered by the user
     */
    public void execute(String keyword) {
        searchUserInteractor.execute(new SearchUserInputData(keyword));
    }

    /**
     * Opens the account page of a user from the results.
     * @param username the account to open
     * @param displayName that account's display name
     */
    public void openProfile(String username, String displayName) {
        getProfileInteractor.execute(new GetProfileInputData(username, displayName));
    }

    /**
     * Returns to the home page.
     */
    public void switchToHomePageView() {
        viewManagerModel.switchView(homePageViewName);
    }
}
