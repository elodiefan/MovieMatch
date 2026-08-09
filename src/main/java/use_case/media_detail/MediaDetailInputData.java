package use_case.media_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Input data for the Media Detail Use Case.
 */
public class MediaDetailInputData {

    private final int mediaId;
    private final String mediaType;
    private final String title;
    private final int releaseYear;
    private final double averageRating;
    private final List<String> genreNames;
    private final String language;
    private final String overview;
    private final String posterPath;

    public MediaDetailInputData(final int mediaId, final String mediaType,
                                final String title, final int releaseYear,
                                final double averageRating,
                                final List<String> genreNames,
                                final String language, final String overview,
                                final String posterPath) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.genreNames = new ArrayList<>(genreNames);
        this.language = language;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public List<String> getGenreNames() {
        return new ArrayList<>(genreNames);
    }

    public String getLanguage() {
        return language;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
