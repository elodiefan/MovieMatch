package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/** The controller for the Login Use Case. */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /** Executes the Login Use Case. */
    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(
                username, password);

        loginUseCaseInteractor.execute(loginInputData);
    }

    /** Executes the sign up view use case. */
    public void switchToSignUpView() {
        loginUseCaseInteractor.switchToSignUpView();
    }

    /** Executes the home page view use case. */
    public void switchToHomePageView() {
        loginUseCaseInteractor.switchToHomePageView();
    }
}
