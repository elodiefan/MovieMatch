package interface_adapter.logout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;
import use_case.logout.LogoutOutputData;

/** Tests for the logout interface adapters. */
class LogoutAdapterTest {

    @Test
    void controllerPassesUsernameToUseCase() {
        final RecordingInputBoundary boundary = new RecordingInputBoundary();

        new LogoutController(boundary).execute("bob");

        assertEquals("bob", boundary.input.getUsername());
    }

    @Test
    void presenterClearsSessionAndSwitchesToLogin() {
        final ViewManagerModel viewManager = new ViewManagerModel();
        final LoggedInViewModel loggedIn = new LoggedInViewModel();
        loggedIn.getState().setUsername("bob");
        final LoginViewModel login = new LoginViewModel();
        login.getState().setUsername("bob");
        login.getState().setPassword("password");

        new LogoutPresenter(viewManager, loggedIn, login)
                .prepareSuccessView(new LogoutOutputData("bob", false));

        assertEquals("", loggedIn.getState().getUsername());
        assertEquals("", login.getState().getUsername());
        assertEquals("", login.getState().getPassword());
        assertEquals(LoginViewModel.VIEW_NAME, viewManager.getState());
    }

    @Test
    void stateAndViewModelStoreUsername() {
        final LogoutViewModel viewModel = new LogoutViewModel();
        viewModel.getState().setUsername("bob");

        assertEquals("bob", viewModel.getState().getUsername());
        assertEquals("logout confirm", viewModel.getViewName());
    }

    private static final class RecordingInputBoundary implements LogoutInputBoundary {
        private LogoutInputData input;

        @Override
        public void execute(final LogoutInputData logoutInputData) {
            input = logoutInputData;
        }
    }
}
