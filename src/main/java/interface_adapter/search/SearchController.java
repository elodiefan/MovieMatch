package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * The controller for the Search Use Case.
 */
public class SearchController {

    private final SearchInputBoundary searchUseCaseInteractor;

    public SearchController(SearchInputBoundary searchUseCaseInteractor) {
        this.searchUseCaseInteractor = searchUseCaseInteractor;
    }

    /**
     * Executes the Search Use Case.
     */
    public void execute(String keyword) {
        final SearchInputData searchInputData =
                new SearchInputData(keyword);

        searchUseCaseInteractor.execute(searchInputData);
    }
}
