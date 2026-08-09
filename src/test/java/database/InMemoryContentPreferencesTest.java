package database;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import use_case.recommendation.AdultContentPreferenceDataAccessInterface;
import use_case.settings.ContentPreferenceDataAccessInterface;

/**
 * Tests the object the settings and recommendation use cases share.
 */
class InMemoryContentPreferencesTest {

    private InMemoryContentPreferences preferences;

    @BeforeEach
    void setUp() {
        preferences = new InMemoryContentPreferences();
    }

    @Test
    @DisplayName("adult content is off until somebody turns it on")
    void offByDefault() {
        assertFalse(preferences.isAdultContentAllowed(),
                "a fresh run must not offer adult titles");
    }

    @Test
    @DisplayName("what settings writes is what recommendations read")
    void writeIsVisibleToTheReader() {
        // The point of one shared object: the two use cases only ever see it
        // through their own interface, but it is the same value underneath.
        final ContentPreferenceDataAccessInterface writer = preferences;
        final AdultContentPreferenceDataAccessInterface reader = preferences;

        writer.setAdultContentAllowed(true);

        assertTrue(reader.isAdultContentAllowed());
    }

    @Test
    @DisplayName("turning it back off is visible too")
    void turningItOffIsVisible() {
        final ContentPreferenceDataAccessInterface writer = preferences;
        final AdultContentPreferenceDataAccessInterface reader = preferences;

        writer.setAdultContentAllowed(true);
        writer.setAdultContentAllowed(false);

        assertFalse(reader.isAdultContentAllowed());
    }

    @Test
    @DisplayName("setting the same value twice changes nothing")
    void repeatedWritesAreHarmless() {
        preferences.setAdultContentAllowed(true);
        preferences.setAdultContentAllowed(true);
        assertTrue(preferences.isAdultContentAllowed());

        preferences.setAdultContentAllowed(false);
        preferences.setAdultContentAllowed(false);
        assertFalse(preferences.isAdultContentAllowed());
    }

    @Test
    @DisplayName("a write on one thread is seen by a reader on another")
    void theValueCrossesThreads() throws InterruptedException {
        // Settings is written on the UI thread and read on a background thread
        // while recommendations load, so the two have to agree on the value.
        preferences.setAdultContentAllowed(true);

        final boolean[] seen = new boolean[1];
        final Thread reader = new Thread(() -> seen[0] = preferences.isAdultContentAllowed());
        reader.start();
        reader.join();

        assertTrue(seen[0], "a background reader must see the choice just made");
    }
}
