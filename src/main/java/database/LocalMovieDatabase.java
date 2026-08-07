package database;

import java.util.List;

import entity.Movie;

/**
 * A local database that stores some movies in case API doesn't work.
 */
public class LocalMovieDatabase {

    private final List<Movie> movies;

    /**
     * Creates a local movie database.
     */
    public LocalMovieDatabase(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Return all movies stored in database.
     */
    public List<Movie> getMovies() {
        return movies;
    }
}
