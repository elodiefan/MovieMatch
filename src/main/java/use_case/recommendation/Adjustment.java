package use_case.recommendation;

/**
 * A small nudge to one candidate's score, with a sentence explaining it.
 *
 * The recommendation score is deterministic. An adjuster may shift it slightly
 * to capture nuance the fixed formula cannot see from genre and cast alone —
 * tone, theme, how a film actually feels — but only slightly, and only for
 * candidates that already scored well.
 */
public class Adjustment {

    /** An adjustment that changes nothing and says nothing. */
    public static final Adjustment NONE = new Adjustment(0.0, "");

    private final double delta;
    private final String explanation;

    /**
     * Creates an adjustment.
     *
     * @param delta how much to shift the score, positive or negative
     * @param explanation a short sentence on why this title suits the user
     */
    public Adjustment(final double delta, final String explanation) {
        this.delta = delta;
        this.explanation = explanation;
    }

    /**
     * Returns the score shift.
     *
     * @return the delta, which callers are expected to clamp before applying
     */
    public double getDelta() {
        return this.delta;
    }

    /**
     * Returns the explanation.
     *
     * @return a short sentence, or an empty string if none was produced
     */
    public String getExplanation() {
        return this.explanation;
    }
}
