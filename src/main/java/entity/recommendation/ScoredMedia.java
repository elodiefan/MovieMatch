package entity.recommendation;

import entity.Media;

/**
 * A candidate together with the score it earned.
 * <p>
 * Carries the {@link SubScoreBreakdown} as well as the total, so the reason for
 * a suggestion travels with it and nothing downstream has to score anything
 * again.
 * <p>
 * Ordering is by score descending, so sorting a list of these puts the best
 * suggestions first.
 */
public class ScoredMedia implements Comparable<ScoredMedia> {

    private final Media media;
    private final double score;
    private final SubScoreBreakdown breakdown;
    private final String explanation;

    /**
     * Creates a scored candidate with no explanation attached.
     *
     * @param media the candidate
     * @param score its final score
     * @param breakdown the per-factor values behind that score
     */
    public ScoredMedia(final Media media, final double score, final SubScoreBreakdown breakdown) {
        this(media, score, breakdown, "");
    }

    /**
     * Creates a scored candidate.
     *
     * @param media the candidate
     * @param score its final score
     * @param breakdown the per-factor values behind that score
     * @param explanation a short sentence on why it was suggested, possibly empty
     */
    public ScoredMedia(final Media media, final double score, final SubScoreBreakdown breakdown,
                       final String explanation) {
        this.media = media;
        this.score = score;
        this.breakdown = breakdown;
        this.explanation = explanation;
    }

    /**
     * Returns a copy of this result with its score shifted and an explanation added.
     * <p>
     * Used when an adjuster nudges the deterministic ranking. Returning a new
     * object rather than mutating keeps the original score auditable.
     *
     * @param delta how much to shift the score by, already clamped by the caller
     * @param newExplanation the explanation to attach
     * @return a new scored candidate
     */
    public ScoredMedia withAdjustment(final double delta, final String newExplanation) {
        return new ScoredMedia(this.media, this.score + delta, this.breakdown, newExplanation);
    }

    /**
     * Returns the candidate.
     *
     * @return the media
     */
    public Media getMedia() {
        return this.media;
    }

    /**
     * Returns the final score.
     *
     * @return the score
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Returns the per-factor values behind the score.
     *
     * @return the breakdown
     */
    public SubScoreBreakdown getBreakdown() {
        return this.breakdown;
    }

    /**
     * Returns why this was suggested.
     *
     * @return the explanation, or an empty string if none was produced
     */
    public String getExplanation() {
        return this.explanation;
    }

    /**
     * Orders by score, highest first.
     *
     * @param other the result to compare against
     * @return a negative value if this scores higher than other
     */
    @Override
    public int compareTo(final ScoredMedia other) {
        return Double.compare(other.score, this.score);
    }
}
