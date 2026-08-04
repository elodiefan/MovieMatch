package interface_adapter.view_lists;

import interface_adapter.ViewManagerModel;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;
import use_case.get_watchlist.GetWatchlistOutputBoundary;

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
    public void switchToAccountView() {
        final AccountState accountState = accountViewModel.getState();
        accountState.setUsername(viewListsViewModel.getState().getUsername());
        accountViewModel.setState(accountState);
        accountViewModel.firePropertyChanged();
        viewManagerModel.setState(accountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
