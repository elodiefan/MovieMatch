package interface_adapter.home_page;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;
import use_case.home_page.HomePageOutputBoundary;
import use_case.home_page.HomePageOutputData;

/**
 * The Presenter for the Home Page Use Case.
 */

public class HomePagePresenter implements HomePageOutputBoundary {

    private HomePageViewModel homePageViewModel;
    private ViewManagerModel viewManagerModel;
    private SearchViewModel searchViewModel;
    private AccountViewModel accountViewModel;

    public HomePagePresenter(ViewManagerModel viewManagerModel, HomePageViewModel homePageViewModel,
                             SearchViewModel searchViewModel, AccountViewModel accountViewModel) {

        this.homePageViewModel = homePageViewModel;
        this.searchViewModel = searchViewModel;
        this.accountViewModel = accountViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSearchSuccessView(HomePageOutputData response) {
        final SearchState searchState = searchViewModel.getState();
        searchViewModel.setState(searchState);
        searchViewModel.firePropertyChanged();

        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareAccountSuccessView(HomePageOutputData response) {
        final AccountState accountState = accountViewModel.getState();
        accountViewModel.setState(accountState);
        accountViewModel.firePropertyChanged();

        viewManagerModel.setState(accountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareRecommendationsView(HomePageOutputData response) {
        final HomePageState homePageState = homePageViewModel.getState();
        homePageViewModel.firePropertyChanged();
    }
}

