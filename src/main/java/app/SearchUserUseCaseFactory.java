package app;

import data_access.UserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserPresenter;
import interface_adapter.search_user.SearchUserViewModel;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.search_user.SearchUserInputBoundary;
import use_case.search_user.SearchUserInteractor;
import use_case.search_user.SearchUserOutputBoundary;
import view.SearchUserView;

/**
 * Factory for assembling the Search User Use Case to avoid direct changing of appbuilder.
 * <p>
 * Follows the same convention as SearchUseCaseFactory: everything this
 * use case needs is built here, so adding it costs AppBuilder one call
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
