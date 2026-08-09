package database;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Genre;
import entity.TVShow;

/**
 * A local database storing TV show information.
 */
public class LocalTvShowDatabase {

    private static final String TV_SHOWS_FILE = "/tvshows.json";

    private final List<TVShow> tvShows;

    /**
     * Creates a local TV show database by loading tvshows.json.
     */
    public LocalTvShowDatabase() {
        this.tvShows = loadTvShows();
    }

    /**
     * Creates a local TV show database using supplied TV shows.
     *
     * @param tvShows the TV shows stored inside the database
     */
    public LocalTvShowDatabase(List<TVShow> tvShows) {
        this.tvShows = new ArrayList<>(tvShows);
    }

    /**
     * Returns TV shows whose titles contain the complete keyword phrase.
     * Matching is not case-sensitive.
     *
     * @param keyword keyword entered by the user
     * @return matching TV shows
     */
    public List<TVShow> search(String keyword) {
        final List<TVShow> matchingTvShows = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            final String normalizedKeyword =
                    keyword.trim().toLowerCase(Locale.ROOT);

            for (TVShow tvShow : tvShows) {
                final String normalizedTitle =
                        tvShow.getTitle().toLowerCase(Locale.ROOT);

                if (normalizedTitle.contains(normalizedKeyword)) {
                    matchingTvShows.add(tvShow);
                }
            }
        }

        return matchingTvShows;
    }

    /**
     * Returns all TV shows stored in the database.
     *
     * @return all TV shows
     */
    public List<TVShow> getTvShows() {
        return new ArrayList<>(tvShows);
    }

    /**
     * Loads TV shows from tvshows.json.
     *
     * @return TV shows loaded from the resource file
     * @throws IllegalStateException the exception thrown
     */
    private List<TVShow> loadTvShows() {
        final List<TVShow> loadedTvShows = new ArrayList<>();
        final ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream =
                     LocalTvShowDatabase.class.getResourceAsStream(
                             TV_SHOWS_FILE
                     )) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Cannot find resource " + TV_SHOWS_FILE
                );
            }

            final JsonNode root = objectMapper.readTree(inputStream);

            for (JsonNode tvShowNode : root) {
                loadedTvShows.add(parseTvShow(tvShowNode));
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load TV shows from " + TV_SHOWS_FILE,
                    exception
            );
        }

        return loadedTvShows;
    }

    /**
     * Converts one JSON object into a TVShow.
     *
     * @param tvShowNode TV show JSON object
     * @return converted TV show
     */
    private TVShow parseTvShow(JsonNode tvShowNode) {
        final int id = tvShowNode.path("id").asInt();
        final String title = tvShowNode.path("title").asText();
        final int releaseYear =
                tvShowNode.path("releaseYear").asInt();
        final double averageRating =
                tvShowNode.path("averageRating").asDouble();
        final List<Genre> genres =
                parseGenres(tvShowNode.path("genres"));
        final String language =
                tvShowNode.path("language").asText();
        final List<String> cast =
                parseCast(tvShowNode.path("cast"));
        final int numberOfSeasons =
                tvShowNode.path("numberOfSeasons").asInt();
        final int numberOfEpisodes =
                tvShowNode.path("numberOfEpisodes").asInt();
        final String overview =
                tvShowNode.path("overview").asText("");
        final String posterPath =
                tvShowNode.path("posterPath").asText("");

        return new TVShow(
                id,
                title,
                releaseYear,
                averageRating,
                genres,
                language,
                cast,
                numberOfSeasons,
                numberOfEpisodes,
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
     * Converts a cast JSON array into cast names.
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
