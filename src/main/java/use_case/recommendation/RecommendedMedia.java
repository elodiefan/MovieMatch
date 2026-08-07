package use_case.recommendation;

/**
 * One suggestion, ready for a screen to display.
 * <p>
 * Everything here is already formatted or resolved, so presenters and views do
 * no arithmetic and no lookups — they read fields and put them on screen. That
 * is what lets the same result feed both the home page list and the detailed
 * view without either of them knowing how a score was reached.
 */
public class RecommendedMedia {

    private final int mediaId;
    private final String title;
    private final int releaseYear;
    private final double score;
    private final String primaryGenre;
    private final String explanation;

    /**
     * Creates a suggestion.
     *
     * @param mediaId the title's id
     * @param title the title's name
     * @param releaseYear the year it came out
     * @param score its final recommendation score
     * @param primaryGenre the genre it is grouped under
     * @param explanation why it was suggested, possibly empty
     */
    public RecommendedMedia(final int mediaId, final String title, final int releaseYear,
                            final double score, final String primaryGenre,
                            final String explanation) {
        this.mediaId = mediaId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.score = score;
        this.primaryGenre = primaryGenre;
        this.explanation = explanation;
    }

    /**
     * Returns the title's id.
     *
     * @return the media id
     */
    public int getMediaId() {
        return this.mediaId;
    }

    /**
     * Returns the title's name.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the year it came out.
     *
     * @return the release year
     */
    public int getReleaseYear() {
        return this.releaseYear;
    }

    /**
     * Returns the recommendation score.
     *
     * @return the score, normally between 0 and 1
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Returns the genre this is grouped under.
     *
     * @return the primary genre name
     */
    public String getPrimaryGenre() {
        return this.primaryGenre;
    }

    /**
     * Returns why this was suggested.
     *
     * @return the explanation, or an empty string if none was produced
     */
    public String getExplanation() {
        return this.explanation;
    }
}
