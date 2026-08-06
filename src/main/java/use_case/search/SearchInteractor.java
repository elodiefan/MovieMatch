package use_case.search;

import java.util.List;

import entity.Media;

/**
 * The Search Interactor.
 */
public class SearchInteractor implements SearchInputBoundary {

    private final SearchMediaDataAccess searchDataAccess;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(SearchMediaDataAccess searchDataAccess,
                            SearchOutputBoundary searchPresenter) {
        this.searchDataAccess = searchDataAccess;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void execute(SearchInputData inputData) {

        final String keyword = inputData.getKeyword();

        if (keyword == null || keyword.trim().isEmpty()) {

            searchPresenter.prepareFailView(
                    "You have to enter at least one word"
            );

        }
        else {

            final List<Media> results =
                    searchDataAccess.search(keyword);

            final SearchOutputData outputData =
                    new SearchOutputData(results);

            searchPresenter.prepareSuccessView(outputData);
        }
    }
}
