package use_case.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Change Settings Interactor.
 */
class SettingsInteractorTest {

    /**
     * Keeps whatever the interactor reports.
     */
    private static final class CapturingPresenter implements SettingsOutputBoundary {
        private SettingsOutputData output;

        @Override
        public void prepareSuccessView(final SettingsOutputData settingsOutputData) {
            this.output = settingsOutputData;
        }
    }

    /**
     * Stands in for the object the recommendation use case reads from.
     */
    private static final class RecordingPreferences
            implements ContentPreferenceDataAccessInterface {
        private boolean adultContentAllowed;
        private int writes;

        @Override
        public void setAdultContentAllowed(final boolean allowed) {
            this.adultContentAllowed = allowed;
            this.writes++;
        }
    }

    private CapturingPresenter presenter;
    private RecordingPreferences preferences;

    @BeforeEach
    void setUp() {
        this.presenter = new CapturingPresenter();
        this.preferences = new RecordingPreferences();
    }

    private void apply(final boolean darkMode, final int textSize,
                       final boolean allowAdultContent) {
        new SettingsInteractor(this.presenter, this.preferences)
                .execute(new SettingsInputData(darkMode, textSize, allowAdultContent));
    }

    @Test
    @DisplayName("the application starts at a readable size inside the slider's range")
    void defaultSizeIsWithinRange() {
        assertTrue(SettingsInteractor.DEFAULT_TEXT_SIZE >= SettingsInteractor.MIN_TEXT_SIZE);
        assertTrue(SettingsInteractor.DEFAULT_TEXT_SIZE <= SettingsInteractor.MAX_TEXT_SIZE);
        assertEquals(22, SettingsInteractor.DEFAULT_TEXT_SIZE);
    }

    @Test
    @DisplayName("a size the user asks for is passed straight back")
    void chosenSizeIsKept() {
        this.apply(false, 30, false);

        assertEquals(30, this.presenter.output.getTextSize());
    }

    @Test
    @DisplayName("a size past either end is pulled back into range")
    void sizeIsClamped() {
        this.apply(false, 500, false);
        assertEquals(SettingsInteractor.MAX_TEXT_SIZE, this.presenter.output.getTextSize());

        this.setUp();
        this.apply(false, -3, false);
        assertEquals(SettingsInteractor.MIN_TEXT_SIZE, this.presenter.output.getTextSize());
    }

    @Test
    @DisplayName("the whole range the slider offers survives the clamp")
    void everySizeOnTheSliderIsAccepted() {
        for (int size = SettingsInteractor.MIN_TEXT_SIZE;
             size <= SettingsInteractor.MAX_TEXT_SIZE;
             size++) {
            this.setUp();
            this.apply(false, size, false);
            assertEquals(size, this.presenter.output.getTextSize(),
                    "the slider must not offer a size the interactor then changes");
        }
    }

    @Test
    @DisplayName("turning adult content on is recorded where recommendations will read it")
    void adultChoiceReachesThePreferences() {
        this.apply(false, 22, true);

        assertTrue(this.preferences.adultContentAllowed);
        assertTrue(this.presenter.output.isAllowAdultContent());
    }

    @Test
    @DisplayName("turning it off again is recorded too")
    void adultChoiceCanBeTurnedBackOff() {
        this.apply(false, 22, true);
        this.apply(false, 22, false);

        assertFalse(this.preferences.adultContentAllowed);
        assertFalse(this.presenter.output.isAllowAdultContent());
        assertEquals(2, this.preferences.writes);
    }

    @Test
    @DisplayName("changing only the text size still says what the content setting is")
    void everyChangeCarriesTheContentSetting() {
        this.apply(true, 18, false);

        assertFalse(this.preferences.adultContentAllowed,
                "a text size change must not quietly switch adult content on");
        assertTrue(this.presenter.output.isDarkMode());
    }
}
