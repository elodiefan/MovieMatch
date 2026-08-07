package entity;

import java.util.List;

/** Represents a movie. */

public class Movie implements Media {

    private final int id;
    private final String title;
    private final int releaseYear;
    private double averageRating;
    private final List<Genre> genres;
    private final String language;
    private final List<String> cast;
    private final int runtime;

    /** Creates a new movie. */
    public Movie(int id,
                 String title,
                 int releaseYear,
                 double averageRating,
                 List<Genre> genres,
                 String language,
                 List<String> cast,
                 int runtime) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.genres = genres;
        this.language = language;
        this.cast = cast;
        this.runtime = runtime;
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
}
