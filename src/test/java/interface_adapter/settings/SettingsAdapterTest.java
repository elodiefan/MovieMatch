package interface_adapter.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInputData;
import use_case.settings.SettingsInteractor;
import use_case.settings.SettingsOutputData;

/**
 * Tests the controller, presenter and state of the settings screen.
 */
class SettingsAdapterTest {

    /**
     * Keeps whatever the controller asked for.
     */
    private static final class RecordingInteractor implements SettingsInputBoundary {
        private SettingsInputData received;
        private int calls;

        @Override
        public void execute(SettingsInputData settingsInputData) {
            this.received = settingsInputData;
            this.calls++;
        }
    }

    private SettingsViewModel viewModel;
    private SettingsPresenter presenter;
    private RecordingInteractor interactor;
    private SettingsController controller;

    @BeforeEach
    void setUp() {
        viewModel = new SettingsViewModel();
        presenter = new SettingsPresenter(viewModel);
        interactor = new RecordingInteractor();
        controller = new SettingsController(interactor);
    }

    @Test
    @DisplayName("the state starts at the default size with adult content off")
    void defaultsAreSafe() {
        final SettingsState state = viewModel.getState();

        assertEquals(SettingsViewModel.DEFAULT_TEXT_SIZE, state.getTextSize());
        assertFalse(state.isDarkMode());
        assertFalse(state.isAllowAdultContent(),
                "adult content has to be off before anyone touches the screen");
    }

    @Test
    @DisplayName("the slider offers exactly the range the use case enforces")
    void rangeMatchesTheUseCase() {
        // Repeating the numbers here would let the slider drift into offering a
        // size the interactor only clamps away again.
        assertEquals(SettingsInteractor.MIN_TEXT_SIZE, SettingsViewModel.MIN_TEXT_SIZE);
        assertEquals(SettingsInteractor.MAX_TEXT_SIZE, SettingsViewModel.MAX_TEXT_SIZE);
        assertEquals(SettingsInteractor.DEFAULT_TEXT_SIZE, SettingsViewModel.DEFAULT_TEXT_SIZE);
    }

    @Test
    @DisplayName("the default is larger than the old one and inside the range")
    void defaultIsTheLargerSize() {
        assertEquals(22, SettingsViewModel.DEFAULT_TEXT_SIZE);
        assertTrue(SettingsViewModel.DEFAULT_TEXT_SIZE > SettingsViewModel.MIN_TEXT_SIZE);
        assertTrue(SettingsViewModel.DEFAULT_TEXT_SIZE < SettingsViewModel.MAX_TEXT_SIZE);
    }

    @Test
    @DisplayName("the controller asks the use case once per change")
    void controllerCallsTheUseCase() {
        controller.execute(true, 30, true);

        assertEquals(1, interactor.calls);
    }

    @Test
    @DisplayName("a choice on the screen reaches the state, and the preference reaches storage")
    void controllerReachesTheStateThroughTheUseCase() {
        // Driven through the real interactor and presenter, so this covers the
        // whole path a click actually takes rather than one link of it.
        final boolean[] stored = new boolean[1];
        final int chosenSize = SettingsInteractor.MAX_TEXT_SIZE - 1;
        final SettingsController wired = new SettingsController(
                new SettingsInteractor(presenter, allowed -> stored[0] = allowed));

        wired.execute(true, chosenSize, true);

        final SettingsState state = viewModel.getState();
        assertTrue(state.isDarkMode());
        assertEquals(chosenSize, state.getTextSize());
        assertTrue(state.isAllowAdultContent());
        assertTrue(stored[0], "the preference has to reach what recommendations read");
    }

    @Test
    @DisplayName("a size beyond the slider is pulled back before it reaches the screen")
    void outOfRangeSizeIsClampedOnTheWayBack() {
        final SettingsController wired = new SettingsController(
                new SettingsInteractor(presenter, allowed -> { }));

        wired.execute(false, 9999, false);

        assertEquals(SettingsViewModel.MAX_TEXT_SIZE, viewModel.getState().getTextSize());
    }

    @Test
    @DisplayName("the presenter writes what was applied onto the screen's state")
    void presenterUpdatesTheState() {
        presenter.prepareSuccessView(new SettingsOutputData(true, 26, true));

        final SettingsState state = viewModel.getState();
        assertTrue(state.isDarkMode());
        assertEquals(26, state.getTextSize());
        assertTrue(state.isAllowAdultContent());
    }

    @Test
    @DisplayName("turning adult content back off is written through too")
    void presenterCanTurnItBackOff() {
        presenter.prepareSuccessView(new SettingsOutputData(false, 22, true));
        presenter.prepareSuccessView(new SettingsOutputData(false, 22, false));

        assertFalse(viewModel.getState().isAllowAdultContent());
    }

    @Test
    @DisplayName("the screen is told when the settings change")
    void listenersAreNotified() {
        final boolean[] told = {false};
        viewModel.addPropertyChangeListener(event -> told[0] = true);

        presenter.prepareSuccessView(new SettingsOutputData(true, 18, false));

        assertTrue(told[0]);
    }

    @Test
    @DisplayName("the screen has a label for every control it shows")
    void everyControlIsLabelled() {
        // A control built without a label draws as a blank box, which is how
        // the block button ended up looking dead.
        assertFalse(SettingsViewModel.DARK_MODE_LABEL.isBlank());
        assertFalse(SettingsViewModel.TEXT_SIZE_LABEL.isBlank());
        assertFalse(SettingsViewModel.ADULT_CONTENT_LABEL.isBlank());
        assertFalse(SettingsViewModel.ADULT_CONTENT_HINT.isBlank());
        assertFalse(SettingsViewModel.BACK_BUTTON_LABEL.isBlank());
        assertFalse(SettingsViewModel.TITLE_LABEL.isBlank());
    }
}
