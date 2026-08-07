package use_case.recommendation;

/** One suggestion, ready for a screen to display. */
public class RecommendedMedia {

    private final int mediaId;
    private final String title;
    private final int releaseYear;
    private final double score;
    private final String primaryGenre;
    private final String explanation;

    /** Creates a suggestion. */
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

    /** Returns the title's id. */
    public int getMediaId() {
        return this.mediaId;
    }

    /** Returns the title's name. */
    public String getTitle() {
        return this.title;
    }

    /** Returns the year it came out. */
    public int getReleaseYear() {
        return this.releaseYear;
    }

    /** Returns the recommendation score. */
    public double getScore() {
        return this.score;
    }

    /** Returns the genre this is grouped under. */
    public String getPrimaryGenre() {
        return this.primaryGenre;
    }

    /** Returns why this was suggested. */
    public String getExplanation() {
        return this.explanation;
    }
}
