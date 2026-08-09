package interface_adapter.filter;

import java.util.ArrayList;
import java.util.List;

import interface_adapter.search_result.SearchResultRow;
import use_case.filter.FilterCriteria;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;
import use_case.search.MediaResultData;

/**
 * Controller for the Filter Use Case.
 */
public class FilterController {

    private final FilterInputBoundary filterUseCaseInteractor;

    public FilterController(
            FilterInputBoundary filterUseCaseInteractor) {
        this.filterUseCaseInteractor = filterUseCaseInteractor;
    }

    /**
     * Executes the Filter Use Case.
     *
     * @param requestModel the filter request model
     */
    public void execute(FilterRequestModel requestModel) {
        final FilterCriteria criteria = new FilterCriteria(
                requestModel.getLanguages(), requestModel.getMinimumRating(),
                requestModel.getGenreIds(), requestModel.getEarliestYear(),
                requestModel.getLatestYear());
        final FilterInputData inputData =
                new FilterInputData(toMediaResultData(
                        requestModel.getOriginalResults()), criteria);
        filterUseCaseInteractor.execute(inputData);
    }

    private List<MediaResultData> toMediaResultData(
            List<SearchResultRow> rows) {
        final List<MediaResultData> mediaResults = new ArrayList<>();
        for (SearchResultRow row : rows) {
            mediaResults.add(new MediaResultData(row.getMediaId(),
                    row.getMediaType(), row.getTitle(),
                    row.getReleaseYear(), row.getAverageRating(),
                    row.getGenreNames(), row.getGenreIds(),
                    row.getLanguage(), row.getOverview(),
                    row.getPosterPath()));
        }
        return mediaResults;
    }
}
