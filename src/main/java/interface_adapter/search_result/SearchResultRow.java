package interface_adapter.search_result;

import java.util.ArrayList;
import java.util.List;

/**
 * Display row for one search result.
 */
public final class SearchResultRow {

    private final int mediaId;
    private final String mediaType;
    private final String title;
    private final int releaseYear;
    private final double averageRating;
    private final List<String> genreNames;
    private final List<Integer> genreIds;
    private final String language;
    private final String overview;
    private final String posterPath;

    /**
     * Creates a search result row.
     * @param mediaId the media id
     * @param mediaType the media type
     * @param title the title
     * @param releaseYear the release year
     * @param averageRating the average rating
     * @param genreNames the genre names
     * @param genreIds the genre ids
     * @param language the language
     * @param overview the overview
     * @param posterPath the poster path
     */
    public SearchResultRow(final int mediaId, final String mediaType,
                           final String title, final int releaseYear,
                           final double averageRating,
                           final List<String> genreNames,
                           final List<Integer> genreIds,
                           final String language, final String overview,
                           final String posterPath) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.genreNames = new ArrayList<>(genreNames);
        this.genreIds = new ArrayList<>(genreIds);
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

    public List<Integer> getGenreIds() {
        return new ArrayList<>(genreIds);
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
