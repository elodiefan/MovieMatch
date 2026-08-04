package interface_adapter.view_lists;

import interface_adapter.ViewManagerModel;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;
import interface_adapter.home_page.HomePageState;
import use_case.get_watchlist.GetWatchlistOutputBoundary;
import use_case.get_watchlist.GetWatchlistOutputData;
import use_case.login.LoginOutputData;

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
        // On success, switch to the home page view.

        final HomePageState homePageState = homePageViewModel.getState();
        homePageState.setUsername(response.getUsername());
        this.homePageViewModel.setState(homePageState);
        this.homePageViewModel.firePropertyChanged();
        this.viewManagerModel.setState(homePageViewModel.getViewName());
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
