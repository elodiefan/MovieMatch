package interface_adapter.change_display_name;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import interface_adapter.ViewManagerModel;
import interface_adapter.personal_account.PersonalAccountViewModel;
import use_case.change_display_name.ChangeDisplayNameInputBoundary;
import use_case.change_display_name.ChangeDisplayNameInputData;
import use_case.change_display_name.ChangeDisplayNameOutputData;

/** Tests for the change display name interface adapters. */
class ChangeDisplayNameAdapterTest {

    @Test
    void controllerPassesNamesToUseCase() {
        final RecordingBoundary boundary = new RecordingBoundary();
        final ChangeDisplayNameController controller = new ChangeDisplayNameController(
                boundary, new ViewManagerModel(), new PersonalAccountViewModel());

        controller.changeDisplayName("bob", "Bob", "Bobby");

        assertNotNull(boundary.input);
    }

    @Test
    void controllerReturnsToUpdatedPersonalAccount() {
        final ViewManagerModel viewManager = new ViewManagerModel();
        final PersonalAccountViewModel personalAccount = new PersonalAccountViewModel();
        final ChangeDisplayNameController controller = new ChangeDisplayNameController(
                input -> { }, viewManager, personalAccount);

        controller.switchToPersonalAccountView("bob", "Bobby");

        assertEquals("bob", personalAccount.getState().getUsername());
        assertEquals("Bobby", personalAccount.getState().getDisplayName());
        assertEquals(personalAccount.getViewName(), viewManager.getState());
    }

    @Test
    void presenterStoresSuccessAndFailureMessages() {
        final ChangeDisplayNameViewModel viewModel = new ChangeDisplayNameViewModel();
        final ChangeDisplayNamePresenter presenter = new ChangeDisplayNamePresenter(
                new ViewManagerModel(), viewModel);

        presenter.prepareSuccessView(new ChangeDisplayNameOutputData(
                "bob", "Bob", "Bobby"));
        assertEquals("Bobby", viewModel.getState().getOldDisplayName());
        assertEquals("", viewModel.getState().getNewDisplayName());
        assertEquals("Display name changes saved.", viewModel.getState().getMessage());

        presenter.prepareFailView("Display name cannot be empty.");
        assertEquals("Display name cannot be empty.", viewModel.getState().getError());
        assertEquals("", viewModel.getState().getMessage());
    }

    @Test
    void stateStoresAllDisplayNameFields() {
        final ChangeDisplayNameState state = new ChangeDisplayNameViewModel().getState();
        state.setUsername("bob");
        state.setOldDisplayName("Bob");
        state.setNewDisplayName("Bobby");
        state.setMessage("Saved");
        state.setError("None");

        assertEquals("bob", state.getUsername());
        assertEquals("Bob", state.getOldDisplayName());
        assertEquals("Bobby", state.getNewDisplayName());
        assertEquals("Saved", state.getMessage());
        assertEquals("None", state.getError());
    }

    private static final class RecordingBoundary implements ChangeDisplayNameInputBoundary {
        private ChangeDisplayNameInputData input;

        @Override
        public void changeDisplayName(final ChangeDisplayNameInputData inputData) {
            input = inputData;
        }
    }
}
