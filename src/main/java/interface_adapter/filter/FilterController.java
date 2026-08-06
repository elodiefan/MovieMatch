package interface_adapter.filter;

import java.util.List;

import entity.Media;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.filter.FilterCriteria;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;

/**
 * Controller for the Filter Use Case.
 */
public class FilterController {

    private final FilterInputBoundary filterUseCaseInteractor;
    private final SearchResultViewModel searchResultViewModel;

    public FilterController(
            FilterInputBoundary filterUseCaseInteractor,
            SearchResultViewModel searchResultViewModel) {
        this.filterUseCaseInteractor = filterUseCaseInteractor;
        this.searchResultViewModel = searchResultViewModel;
    }

    /**
     * Executes the Filter Use Case.
     *
     * @param criteria the selected filter criteria
     */
    public void execute(FilterCriteria criteria) {
        final SearchResultState state =
                searchResultViewModel.getState();
        final List<Media> originalResults =
                state.getOriginalResults();

        final FilterInputData inputData =
                new FilterInputData(originalResults, criteria);

        filterUseCaseInteractor.execute(inputData);
    }
}
