package use_case.media_detail;

import java.util.List;

import entity.Genre;

/**
 * Output data for the Media Detail Use Case.
 */
public class MediaDetailOutputData {

    private final int mediaId;
    private final String mediaType;

    private final String title;
    private final int releaseYear;
    private final double averageRating;
    private final List<Genre> genres;
    private final String language;

    public MediaDetailOutputData(int mediaId,
                                 String mediaType,
                                 String title,
                                 int releaseYear,
                                 double averageRating,
                                 List<Genre> genres,
                                 String language) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.genres = genres;
        this.language = language;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public String getLanguage() {
        return language;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }
}
