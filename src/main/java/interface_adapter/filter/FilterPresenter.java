package interface_adapter.filter;

import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;

/** Presenter for the Filter Use Case. */
public class FilterPresenter implements FilterOutputBoundary {

    private final SearchResultViewModel searchResultViewModel;

    public FilterPresenter(
            SearchResultViewModel searchResultViewModel) {
        this.searchResultViewModel = searchResultViewModel;
    }

    @Override
    public void prepareSuccessView(FilterOutputData outputData) {
        final SearchResultState state =
                searchResultViewModel.getState();

        state.setResults(outputData.getFilteredResults());
        state.setFilterError(null);

        searchResultViewModel.setState(state);
        searchResultViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final SearchResultState state =
                searchResultViewModel.getState();

        state.setFilterError(error);

        searchResultViewModel.setState(state);
        searchResultViewModel.firePropertyChanged();
    }
}
