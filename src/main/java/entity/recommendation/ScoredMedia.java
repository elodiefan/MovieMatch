package entity.recommendation;

import entity.Media;

/** A candidate together with the score it earned. */
public class ScoredMedia implements Comparable<ScoredMedia> {

    private final Media media;
    private final double score;
    private final SubScoreBreakdown breakdown;
    private final String explanation;

    /** Creates a scored candidate with no explanation attached. */
    public ScoredMedia(final Media media, final double score, final SubScoreBreakdown breakdown) {
        this(media, score, breakdown, "");
    }

    /** Creates a scored candidate. */
    public ScoredMedia(final Media media, final double score, final SubScoreBreakdown breakdown,
                       final String explanation) {
        this.media = media;
        this.score = score;
        this.breakdown = breakdown;
        this.explanation = explanation;
    }

    /** Returns a copy of this result with its score shifted and an explanation added. */
    public ScoredMedia withAdjustment(final double delta, final String newExplanation) {
        return new ScoredMedia(this.media, this.score + delta, this.breakdown, newExplanation);
    }

    /** Returns the candidate. */
    public Media getMedia() {
        return this.media;
    }

    /** Returns the final score. */
    public double getScore() {
        return this.score;
    }

    /** Returns the per-factor values behind the score. */
    public SubScoreBreakdown getBreakdown() {
        return this.breakdown;
    }

    /** Returns why this was suggested. */
    public String getExplanation() {
        return this.explanation;
    }

    /** Orders by score, highest first. */
    @Override
    public int compareTo(final ScoredMedia other) {
        return Double.compare(other.score, this.score);
    }
}
