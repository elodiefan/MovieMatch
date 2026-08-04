package interface_adapter.view_lists;

import interface_adapter.ViewManagerModel;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;
import use_case.get_watchlist.GetWatchlistOutputBoundary;
import use_case.get_watchlist.GetWatchlistOutputData;

public class ViewListsPresenter implements GetWatchlistOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ViewListsViewModel viewListsViewModel;
    private final AccountViewModel accountViewModel;

    public ViewListsPresenter(ViewManagerModel viewManagerModel,
                              ViewListsViewModel viewListsViewModel,
                              AccountViewModel accountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewListsViewModel = viewListsViewModel;
        this.accountViewModel = accountViewModel;
    }

    @Override
    public void prepareSuccessView(GetWatchlistOutputData response) {
        // On success, switch to the view lists view.
        final ViewListsState viewListsState = viewListsViewModel.getState();
        viewListsState.setUsername(response.getUsername());
        viewListsState.setDisplayName(response.getDisplayName());
        viewListsState.setDisplayText(response.getWatchlist());
        this.viewListsViewModel.setState(viewListsState);
        this.viewListsViewModel.firePropertyChanged();
        this.viewManagerModel.setState(viewListsViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToAccountView() {
        final AccountState accountState = accountViewModel.getState();
        accountState.setUsername(viewListsViewModel.getState().getUsername());
        accountViewModel.setState(accountState);
        accountViewModel.firePropertyChanged();
        viewManagerModel.setState(accountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
