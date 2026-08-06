package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * Presenter for Search Use Case.
 */
public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final SearchResultViewModel searchResultViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchResultViewModel searchResultViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.searchResultViewModel = searchResultViewModel;
    }

    @Override
    public void prepareFailView(String error) {

        final SearchState searchState = searchViewModel.getState();

        searchState.setSearchError(error);

        searchViewModel.setState(searchState);
        searchViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(SearchOutputData outputData) {

        final SearchResultState state =
                searchResultViewModel.getState();

        state.setOriginalResults(outputData.getResults());
        state.setResults(outputData.getResults());

        searchResultViewModel.setState(state);
        searchResultViewModel.firePropertyChanged();

        viewManagerModel.setState(
                SearchResultViewModel.VIEW_NAME
        );
        viewManagerModel.firePropertyChanged();
    }
}
