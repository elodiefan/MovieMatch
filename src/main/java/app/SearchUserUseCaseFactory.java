package app;

import database.UserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserPresenter;
import interface_adapter.search_user.SearchUserViewModel;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.search_user.SearchUserInputBoundary;
import use_case.search_user.SearchUserInteractor;
import use_case.search_user.SearchUserOutputBoundary;
import views.SearchUserView;

/**
 * Factory for assembling the Search User Use Case to avoid direct changing of appbuilder.
 * <p>
 * Follows the same convention as {@code SearchUseCaseFactory}: everything this
 * use case needs is built here, so adding it costs {@code AppBuilder} one call
 * instead of twenty lines. That matters on a file several people edit at once.
 */
public final class SearchUserUseCaseFactory {

    /**
     * Prevents this utility class from being instantiated.
     */
    private SearchUserUseCaseFactory() {
    }

    /**
     * Creates and connects the Search User Use Case.
     *
     * @param viewManagerModel manages switching between views
     * @param searchUserViewModel stores the search state and its results
     * @param searchUserView the view receiving the controller
     * @param userDataAccessObject the store to search
     * @param getProfileInteractor reused so a result can open that user's account page
     * @param homePageViewName where the Back button goes
     */
    public static void create(
            ViewManagerModel viewManagerModel,
            SearchUserViewModel searchUserViewModel,
            SearchUserView searchUserView,
            UserDataAccessObject userDataAccessObject,
            GetProfileInputBoundary getProfileInteractor,
            String homePageViewName) {

        final SearchUserOutputBoundary searchUserPresenter =
                new SearchUserPresenter(searchUserViewModel);

        final SearchUserInputBoundary searchUserInteractor =
                new SearchUserInteractor(userDataAccessObject, searchUserPresenter);

        final SearchUserController searchUserController =
                new SearchUserController(
                        searchUserInteractor,
                        getProfileInteractor,
                        viewManagerModel,
                        homePageViewName);

        searchUserView.setSearchUserController(searchUserController);
    }
}
