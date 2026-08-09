package interface_adapter.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import interface_adapter.ViewManagerModel;
import interface_adapter.search_result.SearchResultRow;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.search.MediaResultData;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * Presenter for Search Use Case.
 */
public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final Executor uiExecutor;
    private final SearchResultViewModel searchResultViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SearchResultViewModel searchResultViewModel,
                             Executor uiExecutor) {

        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.uiExecutor = uiExecutor;
        this.searchResultViewModel = searchResultViewModel;
    }

    @Override
    public void prepareFailView(String error) {
        // Searching runs off the UI thread so the window stays responsive, so
        // the results have to be handed back to it before any view is touched.
        onUiThread(() -> {
            final SearchState searchState = searchViewModel.getState();

            searchState.setSearchError(error);

            searchViewModel.setState(searchState);
            searchViewModel.firePropertyChanged();
        });
    }

    @Override
    public void prepareSuccessView(SearchOutputData outputData) {
        onUiThread(() -> {
            final SearchResultState state =
                    searchResultViewModel.getState();
            final List<SearchResultRow> combined;
            if (outputData.isAppending()) {
                combined = new ArrayList<>(state.getOriginalResults());
                combined.addAll(toSearchResultRows(outputData.getResults()));
            }
            else {
                combined = toSearchResultRows(outputData.getResults());
            }
            state.setOriginalResults(combined);
            state.setResults(combined);
            state.setKeyword(outputData.getKeyword());
            state.setNextPage(outputData.getNextPage());
            state.setMoreAvailable(outputData.isMoreAvailable());
            state.setTotalResults(outputData.getTotalResults());
            searchResultViewModel.setState(state);
            searchResultViewModel.firePropertyChanged();
            if (!outputData.isAppending()) {
                viewManagerModel.setState(SearchResultViewModel.VIEW_NAME);
                viewManagerModel.firePropertyChanged();
            }
        });
    }

    /**
     * Runs an update on the UI thread, whichever thread called in.
     *
     * @param update the update
     */
    private void onUiThread(Runnable update) {
        uiExecutor.execute(update);
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
