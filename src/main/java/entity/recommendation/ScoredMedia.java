package entity.recommendation;

import entity.Media;

/**
 * A candidate together with the score it earned.
 */
public class ScoredMedia implements Comparable<ScoredMedia> {

    private final Media media;
    private final double score;
    private final SubScoreBreakdown breakdown;
    private final String explanation;

    /**
     * Creates a scored candidate with no explanation attached.
     *
     * @param media the media
     * @param score the score
     * @param breakdown the breakdown
     */
    public ScoredMedia(final Media media, final double score, final SubScoreBreakdown breakdown) {
        this(media, score, breakdown, "");
    }

    /**
     * Creates a scored candidate.
     *
     * @param media the media
     * @param score the score
     * @param breakdown the breakdown
     * @param explanation the explanation
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
     *
     * @param delta the delta
     * @param newExplanation the new explanation
     * @return the with adjustment
     */
    public ScoredMedia withAdjustment(final double delta, final String newExplanation) {
        return new ScoredMedia(this.media, this.score + delta, this.breakdown, newExplanation);
    }

    /**
     * Returns the candidate.
     *
     * @return the get media
     */
    public Media getMedia() {
        return this.media;
    }

    /**
     * Returns the final score.
     *
     * @return the get score
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Returns the per-factor values behind the score.
     *
     * @return the get breakdown
     */
    public SubScoreBreakdown getBreakdown() {
        return this.breakdown;
    }

    /**
     * Returns why this was suggested.
     *
     * @return the get explanation
     */
    public String getExplanation() {
        return this.explanation;
    }

    /**
     * Orders by score, highest first.
     *
     * @param other the other
     * @return the compare to
     */
    @Override
    public int compareTo(final ScoredMedia other) {
        return Double.compare(other.score, this.score);
    }
}
