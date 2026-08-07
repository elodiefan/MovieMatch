package app;

import data_access.TmdbApiClient;
import data_access.TmdbSearchMediaDataAccess;
import view.SearchResultView;
import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchMediaDataAccess;
import use_case.search.SearchOutputBoundary;
import view.SearchView;

/**
 * Factory for assembling the Search Use Case to avoid direct changing of appbuilder.
 */
public final class SearchUseCaseFactory {

    /**
     * Prevents this utility class from being instantiated.
     */
    private SearchUseCaseFactory() {
    }

    /**
     * Creates and connects the Search Use Case.
     */
    public static void create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchResultViewModel searchResultViewModel,
            SearchView searchView,
            SearchResultView searchResultView) {

        final TmdbApiClient tmdbApiClient =
                new TmdbApiClient();

        final SearchMediaDataAccess searchDataAccess =
                new TmdbSearchMediaDataAccess(tmdbApiClient);

        final SearchOutputBoundary searchPresenter =
                new SearchPresenter(
                        viewManagerModel,
                        searchViewModel,
                        searchResultViewModel
                );

        final SearchInputBoundary searchInteractor =
                new SearchInteractor(
                        searchDataAccess,
                        searchPresenter
                );

        final SearchController searchController =
                new SearchController(searchInteractor);

        searchView.setSearchController(searchController);
        // The results screen needs it too, to ask for the next block of pages.
        searchResultView.setSearchController(searchController);
    }
}
