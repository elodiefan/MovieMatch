package entity;

import java.util.List;

/**
 * Represents a movie.
 */

public class Movie implements Media {

    private final int id;
    private final String title;
    private final int releaseYear;
    private double averageRating;
    private final List<Genre> genres;
    private final String language;
    private final List<String> cast;
    private final int runtime;
    private final String overview;
    private final String posterPath;

    /**
     * Creates a movie without overview or poster metadata.
     *
     * @param id the id of the movie
     * @param title the title of the movie
     * @param releaseYear the release year of the movie
     * @param averageRating the rating of the movie
     * @param genres the genres of the movie
     * @param language the language of the movie
     * @param cast the cast of the movie
     * @param runtime the runtime of the movie
     */
    public Movie(int id,
                 String title,
                 int releaseYear,
                 double averageRating,
                 List<Genre> genres,
                 String language,
                 List<String> cast,
                 int runtime) {
        this(id, title, releaseYear, averageRating, genres, language, cast,
                runtime, "", "");
    }

    /**
     * Creates a new movie.
     *
     * @param id the id of the movie
     * @param title the title of the movie
     * @param releaseYear the release year of the movie
     * @param averageRating the rating of the movie
     * @param genres the genres of the movie
     * @param language the language of the movie
     * @param cast the cast of the movie
     * @param runtime the runtime of the movie
     * @param overview the overview of the movie
     * @param posterPath the TMDB poster path of the movie
     */
    public Movie(int id,
                 String title,
                 int releaseYear,
                 double averageRating,
                 List<Genre> genres,
                 String language,
                 List<String> cast,
                 int runtime,
                 String overview,
                 String posterPath) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.genres = genres;
        this.language = language;
        this.cast = cast;
        this.runtime = runtime;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    public List<String> getCast() {
        return cast;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public void updateRating(double rating) {
        this.averageRating = rating;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.MOVIE;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public String getPosterPath() {
        return posterPath;
    }
}
