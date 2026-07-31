package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           LoggedInViewModel loggedInViewModel,
                           LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        /*
         * Clear the current session information after logout.
         * The user will be redirected to the login page.
         *
         * The output data is not required because logout only clears session state.
         */
        final LoggedInState loggedInState = new LoggedInState();

        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();

        // Update LoginState
        final LoginState loginState =
                loginViewModel.getState();

        loginState.setUsername("");
        loginState.setPassword("");

        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        // Switch to Login View
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // This should never be called because logout cannot fail.
    }
}
