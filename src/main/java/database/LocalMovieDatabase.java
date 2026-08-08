package database;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Genre;
import entity.Movie;

/**
 * A local database that stores some movies in case API doesn't work.
 */
public class LocalMovieDatabase {

    private static final String MOVIES_FILE = "/movies.json";

    private final List<Movie> movies;

    /**
     * Creates a local movie database by loading movies.json.
     */
    public LocalMovieDatabase() {
        this.movies = loadMovies();
    }

    /**
     * Creates a local movie database using the supplied movies.
     *
     * @param movies the movies stored inside the database
     */
    public LocalMovieDatabase(List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
    }

    /**
     * Returns movies whose titles contain the complete keyword phrase.
     * Matching is not case-sensitive.
     *
     * @param keyword keyword entered by the user
     * @return matching movies
     */
    public List<Movie> search(String keyword) {
        final List<Movie> matchingMovies = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            final String normalizedKeyword =
                    keyword.trim().toLowerCase(Locale.ROOT);

            for (Movie movie : movies) {
                final String normalizedTitle =
                        movie.getTitle().toLowerCase(Locale.ROOT);

                if (normalizedTitle.contains(normalizedKeyword)) {
                    matchingMovies.add(movie);
                }
            }
        }

        return matchingMovies;
    }

    /**
     * Returns all movies stored in the database.
     *
     * @return all movies
     */
    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    /**
     * Loads movie entities from movies.json.
     *
     * @return movies loaded from the resource file
     */
    private List<Movie> loadMovies() {
        final List<Movie> loadedMovies = new ArrayList<>();
        final ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream =
                     LocalMovieDatabase.class.getResourceAsStream(
                             MOVIES_FILE
                     )) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Cannot find resource " + MOVIES_FILE
                );
            }

            final JsonNode root = objectMapper.readTree(inputStream);

            for (JsonNode movieNode : root) {
                loadedMovies.add(parseMovie(movieNode));
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load movies from " + MOVIES_FILE,
                    exception
            );
        }

        return loadedMovies;
    }

    /**
     * Converts one JSON object into a Movie.
     *
     * @param movieNode movie JSON object
     * @return converted movie
     */
    private Movie parseMovie(JsonNode movieNode) {
        final int id = movieNode.path("id").asInt();
        final String title = movieNode.path("title").asText();
        final int releaseYear =
                movieNode.path("releaseYear").asInt();
        final double averageRating =
                movieNode.path("averageRating").asDouble();
        final List<Genre> genres =
                parseGenres(movieNode.path("genres"));
        final String language =
                movieNode.path("language").asText();
        final List<String> cast =
                parseCast(movieNode.path("cast"));
        final int runtime =
                movieNode.path("runtime").asInt();
        final String overview =
                movieNode.path("overview").asText("");
        final String posterPath =
                movieNode.path("posterPath").asText("");

        return new Movie(
                id,
                title,
                releaseYear,
                averageRating,
                genres,
                language,
                cast,
                runtime,
                overview,
                posterPath
        );
    }

    /**
     * Converts genre JSON objects into Genre entities.
     *
     * @param genreNodes genre JSON array
     * @return converted genres
     */
    private List<Genre> parseGenres(JsonNode genreNodes) {
        final List<Genre> genres = new ArrayList<>();

        for (JsonNode genreNode : genreNodes) {
            genres.add(
                    new Genre(
                            genreNode.path("id").asInt(),
                            genreNode.path("name").asText()
                    )
            );
        }

        return genres;
    }

    /**
     * Converts a cast JSON array into names.
     *
     * @param castNodes cast JSON array
     * @return cast names
     */
    private List<String> parseCast(JsonNode castNodes) {
        final List<String> cast = new ArrayList<>();

        for (JsonNode castNode : castNodes) {
            cast.add(castNode.asText());
        }

        return cast;
    }
}
