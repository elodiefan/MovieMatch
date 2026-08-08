package use_case.recommendation;

/**
 * One suggestion, ready for a screen to display.
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
     * @param mediaId the media id
     * @param title the title
     * @param releaseYear the release year
     * @param score the score
     * @param primaryGenre the primary genre
     * @param explanation the explanation
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
     * @return the get media id
     */
    public int getMediaId() {
        return this.mediaId;
    }

    /**
     * Returns the title's name.
     *
     * @return the get title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the year it came out.
     *
     * @return the get release year
     */
    public int getReleaseYear() {
        return this.releaseYear;
    }

    /**
     * Returns the recommendation score.
     *
     * @return the get score
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Returns the genre this is grouped under.
     *
     * @return the get primary genre
     */
    public String getPrimaryGenre() {
        return this.primaryGenre;
    }

    /**
     * Returns why this was suggested.
     *
     * @return the get explanation
     */
    public String getExplanation() {
        return this.explanation;
    }
}
