package interface_adapter.filter;

import java.util.ArrayList;
import java.util.List;

import interface_adapter.search_result.SearchResultRow;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.filter.FilterOutputBoundary;
import use_case.filter.FilterOutputData;
import use_case.search.MediaResultData;

/**
 * Presenter for the Filter Use Case.
 */
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

        state.setResults(toSearchResultRows(outputData.getFilteredResults()));
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

    private List<SearchResultRow> toSearchResultRows(
            List<MediaResultData> mediaResults) {
        final List<SearchResultRow> rows = new ArrayList<>();
        for (MediaResultData media : mediaResults) {
            rows.add(new SearchResultRow(media.getMediaId(),
                    media.getMediaType(), media.getTitle(),
                    media.getReleaseYear(), media.getAverageRating(),
                    media.getGenreNames(), media.getGenreIds(),
                    media.getLanguage(), media.getOverview(),
                    media.getPosterPath()));
        }
        return rows;
    }
}
