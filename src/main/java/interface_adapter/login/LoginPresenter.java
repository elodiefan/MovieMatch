package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/** The Presenter for the Login Use Case. */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomePageViewModel homePageViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HomePageViewModel homePageViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homePageViewModel = homePageViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the home page view.
        loginViewModel.firePropertyChanged("log in");

        final HomePageState homePageState = homePageViewModel.getState();
        homePageState.setUsername(response.getUsername());
        this.homePageViewModel.setState(homePageState);
        this.homePageViewModel.firePropertyChanged();
        switchToHomePageView();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setState(SignupViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToHomePageView() {
        viewManagerModel.setState(HomePageViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }
}
