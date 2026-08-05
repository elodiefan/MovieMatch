package interface_adapter.get_lists;

import interface_adapter.ViewManagerModel;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;
import use_case.get_lists.get_blocked_users.GetBlockedUsersOutputBoundary;
import use_case.get_lists.get_blocked_users.GetBlockedUsersOutputData;
import use_case.get_lists.get_watch_history.GetWatchHistoryOutputBoundary;
import use_case.get_lists.get_watch_history.GetWatchHistoryOutputData;
import use_case.get_lists.get_watchlist.GetWatchListOutputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistOutputData;

public class GetListsPresenter implements GetWatchListOutputBoundary, GetWatchHistoryOutputBoundary,
        GetBlockedUsersOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final GetListsViewModel getListsViewModel;
    private final AccountViewModel accountViewModel;

    public GetListsPresenter(ViewManagerModel viewManagerModel,
                             GetListsViewModel getListsViewModel,
                             AccountViewModel accountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getListsViewModel = getListsViewModel;
        this.accountViewModel = accountViewModel;
    }

    @Override
    public void prepareSuccessView(GetWatchlistOutputData response) {
        // On success, switch to the view lists view.
        final GetListsState getListsState = getListsViewModel.getState();
        getListsState.setUsername(response.getUsername());
        getListsState.setDisplayName(response.getDisplayName());
        getListsState.setDisplayText(response.getWatchlist());
        this.getListsViewModel.setState(getListsState);
        this.getListsViewModel.firePropertyChanged();
        this.viewManagerModel.setState(getListsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(GetWatchHistoryOutputData response) {
        // On success, switch to the view lists view.
        final GetListsState getListsState = getListsViewModel.getState();
        getListsState.setUsername(response.getUsername());
        getListsState.setDisplayName(response.getDisplayName());
        getListsState.setDisplayText(response.getWatchHistory());
        this.getListsViewModel.setState(getListsState);
        this.getListsViewModel.firePropertyChanged();
        this.viewManagerModel.setState(getListsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(GetBlockedUsersOutputData response) {
        // On success, switch to the view lists view.
        final GetListsState getListsState = getListsViewModel.getState();
        getListsState.setUsername(response.getUsername());
        getListsState.setDisplayName(response.getDisplayName());
        getListsState.setDisplayText(response.getBlockedUsers());
        this.getListsViewModel.setState(getListsState);
        this.getListsViewModel.firePropertyChanged();
        this.viewManagerModel.setState(getListsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToAccountView() {
        final AccountState accountState = accountViewModel.getState();
        accountState.setUsername(getListsViewModel.getState().getUsername());
        accountViewModel.setState(accountState);
        accountViewModel.firePropertyChanged();
        viewManagerModel.setState(accountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
