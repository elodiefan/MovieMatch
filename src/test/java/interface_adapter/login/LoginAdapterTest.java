package interface_adapter.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import use_case.login.LoginOutputData;

/** Tests for the login interface adapters. */
class LoginAdapterTest {

    @Test
    void controllerPassesCredentialsToTheUseCase() {
        final RecordingInputBoundary boundary = new RecordingInputBoundary();
        final LoginController controller = new LoginController(boundary);

        controller.execute("bob", "password");
        controller.switchToSignUpView();
        controller.switchToHomePageView();

        assertNotNull(boundary.input);
        assertEquals(1, boundary.signupSwitches);
        assertEquals(1, boundary.homeSwitches);
    }

    @Test
    void presenterStoresLoginFailure() {
        final LoginViewModel loginViewModel = new LoginViewModel();
        final LoginPresenter presenter = new LoginPresenter(
                new ViewManagerModel(), new HomePageViewModel(), loginViewModel);

        presenter.prepareFailView("Incorrect password.");

        assertEquals("Incorrect password.", loginViewModel.getState().getLoginError());
    }

    @Test
    void presenterStoresUsernameAndSwitchesToHomePageOnSuccess() {
        final ViewManagerModel viewManager = new ViewManagerModel();
        final HomePageViewModel homePage = new HomePageViewModel();
        final LoginViewModel login = new LoginViewModel();
        final LoginPresenter presenter = new LoginPresenter(viewManager, homePage, login);

        presenter.prepareSuccessView(new LoginOutputData("bob", "Bob", false));

        assertEquals("bob", homePage.getState().getUsername());
        assertEquals(HomePageViewModel.VIEW_NAME, viewManager.getState());
    }

    @Test
    void stateAndViewModelStoreLoginFields() {
        final LoginViewModel viewModel = new LoginViewModel();
        final LoginState state = viewModel.getState();

        state.setUsername("bob");
        state.setPassword("password");
        state.setLoginError(null);

        assertEquals("bob", state.getUsername());
        assertEquals("password", state.getPassword());
        assertNull(state.getLoginError());
        assertEquals(LoginViewModel.VIEW_NAME, viewModel.getViewName());
    }

    @Test
    void presenterCanSwitchToSignupPage() {
        final ViewManagerModel viewManager = new ViewManagerModel();
        final LoginPresenter presenter = new LoginPresenter(
                viewManager, new HomePageViewModel(), new LoginViewModel());

        presenter.switchToSignUpView();

        assertEquals(SignupViewModel.VIEW_NAME, viewManager.getState());
    }

    private static final class RecordingInputBoundary implements LoginInputBoundary {
        private LoginInputData input;
        private int signupSwitches;
        private int homeSwitches;

        @Override
        public void execute(final LoginInputData loginInputData) {
            input = loginInputData;
        }

        @Override
        public void switchToSignUpView() {
            signupSwitches++;
        }

        @Override
        public void switchToHomePageView() {
            homeSwitches++;
        }
    }
}
