package interface_adapter.search;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import entity.Media;
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

            final List<Media> combined;
            if (outputData.isAppending()) {
                // Loading more extends what the user is already looking at.
                combined = new ArrayList<>(state.getOriginalResults());
                combined.addAll(outputData.getResults());
            }
            else {
                combined = outputData.getResults();
            }

            state.setOriginalResults(combined);
            state.setResults(combined);
            state.setKeyword(outputData.getKeyword());
            state.setNextPage(outputData.getNextPage());
            state.setMoreAvailable(outputData.isMoreAvailable());
            state.setTotalResults(outputData.getTotalResults());

            searchResultViewModel.setState(state);
            searchResultViewModel.firePropertyChanged();

            // Loading more happens while already on the results screen, so
            // switching view again would only throw away the scroll position.
            if (!outputData.isAppending()) {
                viewManagerModel.setState(
                        SearchResultViewModel.VIEW_NAME
                );
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
        if (SwingUtilities.isEventDispatchThread()) {
            update.run();
        }
        else {
            SwingUtilities.invokeLater(update);
        }
    }
}
