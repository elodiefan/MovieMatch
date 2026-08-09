package use_case.media_detail;

import java.util.List;

/**
 * Output data for the Media Detail Use Case.
 */
public class MediaDetailOutputData {

    private final int mediaId;
    private final String mediaType;

    private final String title;
    private final int releaseYear;
    private final double averageRating;
    private final List<String> genreNames;
    private final String language;
    private final String overview;
    private final String posterPath;

    public MediaDetailOutputData(int mediaId,
                                 String mediaType,
                                 String title,
                                 int releaseYear,
                                 double averageRating,
                                 List<String> genreNames,
                                 String language,
                                 String overview,
                                 String posterPath) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.genreNames = genreNames;
        this.language = language;
        this.overview = overview;
        this.posterPath = posterPath;
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
        return genreNames;
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

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }
}
