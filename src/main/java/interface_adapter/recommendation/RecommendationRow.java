package interface_adapter.recommendation;

/**
 * Display-ready recommendation information for the view model.
 */
public class RecommendationRow {

    private final int mediaId;
    private final String title;
    private final int releaseYear;
    private final double score;
    private final String primaryGenre;
    private final String explanation;

    public RecommendationRow(final int mediaId, final String title,
                             final int releaseYear, final double score,
                             final String primaryGenre,
                             final String explanation) {
        this.mediaId = mediaId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.score = score;
        this.primaryGenre = primaryGenre;
        this.explanation = explanation;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getScore() {
        return score;
    }

    public String getPrimaryGenre() {
        return primaryGenre;
    }

    public String getExplanation() {
        return explanation;
    }
}
