package interface_adapter.media_detail;

import java.util.List;

import entity.Genre;

/**
 * The state for Media Detail View.
 */
public class MediaDetailState {

    private String title;
    private int releaseYear;
    private double averageRating;
    private List<Genre> genres;
    private String language;
    private int mediaId;
    private String mediaType;
    private String mediaDetailError = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaDetailError() {
        return mediaDetailError;
    }

    public void setMediaDetailError(String mediaDetailError) {
        this.mediaDetailError = mediaDetailError;
    }
}
