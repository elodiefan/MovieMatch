package interface_adapter.search_user;

import use_case.search_user.SearchUserOutputBoundary;
import use_case.search_user.SearchUserOutputData;

/** The Presenter for the Search User Use Case. */
public class SearchUserPresenter implements SearchUserOutputBoundary {

    private final SearchUserViewModel searchUserViewModel;

    public SearchUserPresenter(SearchUserViewModel searchUserViewModel) {
        this.searchUserViewModel = searchUserViewModel;
    }

    @Override
    public void prepareSuccessView(SearchUserOutputData outputData) {
        final SearchUserState state = searchUserViewModel.getState();
        state.setResults(outputData.getResults());
        state.setKeyword(outputData.getKeyword());
        state.setSearchError(null);
        state.setSearched(true);

        searchUserViewModel.setState(state);
        searchUserViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final SearchUserState state = searchUserViewModel.getState();
        state.setSearchError(error);

        searchUserViewModel.setState(state);
        searchUserViewModel.firePropertyChanged();
    }
}
