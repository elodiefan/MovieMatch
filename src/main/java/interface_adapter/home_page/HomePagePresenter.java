package interface_adapter.home_page;

import interface_adapter.ViewManagerModel;
import interface_adapter.account.AccountState;
import interface_adapter.account.AccountViewModel;
//import interface_adapter.search.SearchState;
//import interface_adapter.search.SearchViewModel;
import interface_adapter.other_account.OtherAccountState;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountState;
import interface_adapter.personal_account.PersonalAccountViewModel;
import use_case.get_profile.GetProfileOutputBoundary;
import use_case.get_profile.GetProfileOutputData;
import use_case.home_page.HomePageOutputBoundary;
import use_case.home_page.HomePageOutputData;

/**
 * The Presenter for the Home Page Use Case.
 */

//public class HomePagePresenter implements HomePageOutputBoundary {
//
//    private HomePageViewModel homePageViewModel;
//    private ViewManagerModel viewManagerModel;
//    //private SearchViewModel searchViewModel;
//    private PersonalAccountViewModel personalAccountViewModel;
//
//    public HomePagePresenter(ViewManagerModel viewManagerModel, HomePageViewModel homePageViewModel,
//                            PersonalAccountViewModel personalAccountViewModel) {
//
//        this.homePageViewModel = homePageViewModel;
//      //  this.searchViewModel = searchViewModel;
//        this.personalAccountViewModel = personalAccountViewModel;
//        this.viewManagerModel = viewManagerModel;
//    }
//
////    @Override
////    public void switchToSearchView() {
////        viewManagerModel.setState(searchViewModel.getViewName());
////        viewManagerModel.firePropertyChanged();
////    }
////
//    @Override
//    public void switchToPersonalAccountView(HomePageOutputData response) {
//        final PersonalAccountState personalAccountState = personalAccountViewModel.getState();
//        personalAccountState.setUsername(response.getUsername());
//        personalAccountState.setDisplayName(response.getDisplayName());
//        personalAccountViewModel.setState(personalAccountState);
//        personalAccountViewModel.firePropertyChanged();
//
//        viewManagerModel.setState(personalAccountViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }
//}

public class HomePagePresenter implements GetProfileOutputBoundary {

    private final HomePageViewModel homePageViewModel;
    private final ViewManagerModel viewManagerModel;
    private final AccountViewModel accountViewModel;
    private PersonalAccountViewModel personalAccountViewModel;
    private OtherAccountViewModel otherAccountViewModel;

    public HomePagePresenter(ViewManagerModel viewManagerModel,
                             HomePageViewModel homePageViewModel,
                             AccountViewModel accountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homePageViewModel = homePageViewModel;
        this.accountViewModel = accountViewModel;
    }

//    @Override
//    public void switchToSearchView() {
//        viewManagerModel.setState(searchViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }

    @Override
    public void switchToPersonalAccountView(GetProfileOutputData response) {
        final AccountState accountState = accountViewModel.getState();
        accountState.setUsername(response.getUsername());
        accountState.setDisplayName(response.getDisplayName());
        accountViewModel.setState(accountState);
        accountViewModel.firePropertyChanged();

        viewManagerModel.setState(accountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToOtherAccountView(GetProfileOutputData response) {
        final OtherAccountState otherAccountState = otherAccountViewModel.getState();
        otherAccountState.setUsername(response.getUsername());
        otherAccountState.setDisplayName(response.getDisplayName());
        otherAccountViewModel.setState(otherAccountState);
        otherAccountViewModel.firePropertyChanged();

        viewManagerModel.setState(otherAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
