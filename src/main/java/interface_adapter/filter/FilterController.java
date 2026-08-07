package interface_adapter.filter;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;

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
     */
    public void execute(FilterRequestModel requestModel) {
        final FilterInputData inputData =
                new FilterInputData(requestModel.getOriginalResults(),
                        requestModel.getCriteria());
        filterUseCaseInteractor.execute(inputData);
    }
}
