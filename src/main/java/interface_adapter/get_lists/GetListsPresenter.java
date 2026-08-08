package interface_adapter.get_lists;

import java.util.ArrayList;
import java.util.List;

import interface_adapter.ViewManagerModel;
import interface_adapter.other_account.OtherAccountState;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountState;
import interface_adapter.personal_account.PersonalAccountViewModel;
import use_case.get_blocked_users.GetBlockedUsersOutputBoundary;
import use_case.get_blocked_users.GetBlockedUsersOutputData;
import use_case.get_watch_history.GetWatchHistoryOutputBoundary;
import use_case.get_watch_history.GetWatchHistoryOutputData;
import use_case.get_watch_history.WatchHistoryItemData;
import use_case.get_watchlist.GetWatchlistOutputBoundary;
import use_case.get_watchlist.GetWatchlistOutputData;
import use_case.get_watchlist.WatchlistItemData;

public class GetListsPresenter implements GetWatchlistOutputBoundary, GetWatchHistoryOutputBoundary,
        GetBlockedUsersOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final GetListsViewModel getListsViewModel;
    private final PersonalAccountViewModel personalAccountViewModel;
    private final OtherAccountViewModel otherAccountViewModel;

    public GetListsPresenter(ViewManagerModel viewManagerModel,
                             GetListsViewModel getListsViewModel,
                             PersonalAccountViewModel personalAccountViewModel,
                             OtherAccountViewModel otherAccountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getListsViewModel = getListsViewModel;
        this.personalAccountViewModel = personalAccountViewModel;
        this.otherAccountViewModel = otherAccountViewModel;
    }

    @Override
    public void prepareSuccessView(GetWatchlistOutputData response) {
        // On success, switch to the view lists view.
        final GetListsState getListsState = getListsViewModel.getState();
        getListsState.setUsername(response.getUsername());
        getListsState.setDisplayName(response.getDisplayName());
        getListsState.setListRows(toWatchlistRows(response.getWatchlistItems()));
        getListsState.setListLabel(GetListsViewModel.WATCHLIST);
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
        getListsState.setListRows(toWatchHistoryRows(response.getWatchHistoryItems()));
        getListsState.setListLabel(GetListsViewModel.WATCH_HISTORY);
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
        getListsState.setListRows(new ArrayList<>());
        getListsState.setListLabel(GetListsViewModel.BLOCKED_USERS);
        this.getListsViewModel.setState(getListsState);
        this.getListsViewModel.firePropertyChanged();
        this.viewManagerModel.setState(getListsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToPersonalAccountView() {
        final PersonalAccountState personalAccountState = personalAccountViewModel.getState();
        personalAccountState.setUsername(getListsViewModel.getState().getUsername());
        personalAccountViewModel.setState(personalAccountState);
        personalAccountViewModel.firePropertyChanged();
        viewManagerModel.setState(personalAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToOtherAccountView() {
        final OtherAccountState otherAccountState = otherAccountViewModel.getState();
        otherAccountState.setUsername(getListsViewModel.getState().getUsername());
        otherAccountViewModel.setState(otherAccountState);
        otherAccountViewModel.firePropertyChanged();
        viewManagerModel.setState(otherAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    private List<GetListRow> toWatchlistRows(
            List<WatchlistItemData> watchlistItems) {
        final List<GetListRow> rows = new ArrayList<>();
        for (WatchlistItemData item : watchlistItems) {
            rows.add(new GetListRow(item.getMediaId(), item.getMediaType(),
                    item.getMediaTitle(), item.getLoggedAt(),
                    item.getPosterPath()));
        }
        return rows;
    }

    private List<GetListRow> toWatchHistoryRows(
            List<WatchHistoryItemData> watchHistoryItems) {
        final List<GetListRow> rows = new ArrayList<>();
        for (WatchHistoryItemData item : watchHistoryItems) {
            rows.add(new GetListRow(item.getMediaId(), item.getMediaType(),
                    item.getMediaTitle(), item.getLoggedAt(),
                    item.getPosterPath()));
        }
        return rows;
    }
}
