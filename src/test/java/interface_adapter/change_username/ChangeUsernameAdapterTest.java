package interface_adapter.change_username;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import interface_adapter.ViewManagerModel;
import interface_adapter.personal_account.PersonalAccountViewModel;
import use_case.change_username.ChangeUsernameInputBoundary;
import use_case.change_username.ChangeUsernameInputData;
import use_case.change_username.ChangeUsernameOutputData;

/** Tests for the change username interface adapters. */
class ChangeUsernameAdapterTest {

    @Test
    void controllerPassesUsernamesToUseCase() {
        final RecordingBoundary boundary = new RecordingBoundary();
        final ChangeUsernameController controller = new ChangeUsernameController(
                boundary, new ViewManagerModel(), new PersonalAccountViewModel());

        controller.changeUsername("bob", "bobby", "Bob");

        assertNotNull(boundary.input);
    }

    @Test
    void controllerReturnsToPersonalAccountWithNewUsername() {
        final ViewManagerModel viewManager = new ViewManagerModel();
        final PersonalAccountViewModel personalAccount = new PersonalAccountViewModel();
        final ChangeUsernameController controller = new ChangeUsernameController(
                input -> { }, viewManager, personalAccount);

        controller.switchToPersonalAccountView("bob", "bobby");

        assertEquals("bobby", personalAccount.getState().getUsername());
        assertEquals(personalAccount.getViewName(), viewManager.getState());
    }

    @Test
    void presenterStoresSuccessAndFailureMessages() {
        final ChangeUsernameViewModel viewModel = new ChangeUsernameViewModel();
        final ChangeUsernamePresenter presenter = new ChangeUsernamePresenter(
                new ViewManagerModel(), viewModel);

        presenter.prepareSuccessView(new ChangeUsernameOutputData("bob", "bobby", "Bob"));
        assertEquals("bobby", viewModel.getState().getUsername());
        assertEquals("", viewModel.getState().getNewUsername());
        assertEquals("Username changes saved.", viewModel.getState().getMessage());

        presenter.prepareFailView("Username already exists.");
        assertEquals("Username already exists.", viewModel.getState().getError());
        assertEquals("", viewModel.getState().getMessage());
    }

    @Test
    void stateStoresAllUsernameFields() {
        final ChangeUsernameState state = new ChangeUsernameViewModel().getState();
        state.setUsername("bob");
        state.setNewUsername("bobby");
        state.setDisplayName("Bob");
        state.setMessage("Saved");
        state.setError("None");

        assertEquals("bob", state.getUsername());
        assertEquals("bobby", state.getNewUsername());
        assertEquals("Bob", state.getDisplayName());
        assertEquals("Saved", state.getMessage());
        assertEquals("None", state.getError());
    }

    private static final class RecordingBoundary implements ChangeUsernameInputBoundary {
        private ChangeUsernameInputData input;

        @Override
        public void changeUsername(final ChangeUsernameInputData inputData) {
            input = inputData;
        }
    }
}
