package use_case.recommendation;

/**
 * What the recommendation use case needs to know about the user's content settings.
 *
 * The settings screen writes this preference through its own interface. Keeping
 * the two sides separate means neither use case can reach into the other, and
 * each one only sees the half of the preference it actually needs.
 */
public interface AdultContentPreferenceDataAccessInterface {

    /**
     * Reports whether adult titles may be recommended.
     *
     * @return whether adult titles are allowed
     */
    boolean isAdultContentAllowed();
}
