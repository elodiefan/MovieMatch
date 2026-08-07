package use_case.recommendation;

/** A small nudge to one candidate's score, with a sentence explaining it. */
public class Adjustment {

    /** An adjustment that changes nothing and says nothing. */
    public static final Adjustment NONE = new Adjustment(0.0, "");

    private final double delta;
    private final String explanation;

    /** Creates an adjustment. */
    public Adjustment(final double delta, final String explanation) {
        this.delta = delta;
        this.explanation = explanation;
    }

    /** Returns the score shift. */
    public double getDelta() {
        return this.delta;
    }

    /** Returns the explanation. */
    public String getExplanation() {
        return this.explanation;
    }
}
