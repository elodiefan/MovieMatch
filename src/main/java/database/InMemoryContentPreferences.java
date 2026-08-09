package database;

import use_case.recommendation.AdultContentPreferenceDataAccessInterface;
import use_case.settings.ContentPreferenceDataAccessInterface;

/**
 * Holds the content settings for as long as the application is running.
 * One object satisfies both use cases: settings writes the preference, and
 * recommendations read it. Neither knows about the other.
 * Nothing is written to the database, so the preference lasts a session and
 * starts off again next launch, which matches how dark mode and text size
 * already behave.
 */
public class InMemoryContentPreferences
        implements ContentPreferenceDataAccessInterface,
        AdultContentPreferenceDataAccessInterface {

    /**
     * Off until the user asks for it.
     *
     * Written on the UI thread when the checkbox changes and read on a
     * background thread while recommendations load, so the two threads have to
     * agree on its value.
     */
    private volatile boolean adultContentAllowed;

    @Override
    public void setAdultContentAllowed(boolean allowed) {
        this.adultContentAllowed = allowed;
    }

    @Override
    public boolean isAdultContentAllowed() {
        return this.adultContentAllowed;
    }
}
