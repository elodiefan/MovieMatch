package interface_adapter.home_page;

import interface_adapter.ViewManagerModel;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import use_case.home_page.HomePageOutputBoundary;

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
    public void switchToSearchView() {
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToAccountView() {
        viewManagerModel.setState(accountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}

