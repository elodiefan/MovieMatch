package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Media;

/**
 * Tests how the catalogue asks TMDB for recommendation candidates.
 */
class TMDBMediaCatalogueTest {

    private static final Genre ANIMATION = new Genre(16, "Animation");
    private static final Genre COMEDY = new Genre(35, "Comedy");

    /**
     * Answers every request with one film and records what was asked for.
     */
    private static class RecordingSender implements TMDBAPIClient.RequestSender {
        private final List<String> paths = new ArrayList<>();

        @Override
        public TMDBAPIClient.Response send(HttpRequest request) {
            paths.add(request.uri().toString());
            return new TMDBAPIClient.Response(200, "{\"results\":[{"
                    + "\"id\":42,\"title\":\"A Film\",\"name\":\"A Show\","
                    + "\"release_date\":\"2020-05-01\",\"first_air_date\":\"2019-01-01\","
                    + "\"vote_average\":7.5,\"genre_ids\":[16],"
                    + "\"original_language\":\"en\",\"overview\":\"words\","
                    + "\"poster_path\":\"/abc.jpg\"}]}");
        }

        private boolean anyPathHas(String fragment) {
            return paths.stream().anyMatch(path -> path.contains(fragment));
        }

        private boolean everyDiscoverPathHas(String fragment) {
            return paths.stream()
                    .filter(path -> path.contains("/discover/"))
                    .allMatch(path -> path.contains(fragment));
        }
    }

    private RecordingSender sender;
    private TMDBMediaCatalogue catalogue;

    private void given() {
        sender = new RecordingSender();
        catalogue = new TMDBMediaCatalogue(new TMDBAPIClient(sender, "test-token"));
    }

    @Test
    @DisplayName("a genre search excludes adult keywords when the setting is off")
    void genreSearchFilters() {
        given();

        catalogue.findCandidates(Set.of(ANIMATION), new HashSet<>(), false);

        assertTrue(sender.everyDiscoverPathHas("without_keywords"),
                "every candidate request has to be filtered, not just the first");
    }

    @Test
    @DisplayName("no taste profile falls back to popular, still filtered")
    void popularFallbackFilters() {
        given();

        // An empty genre set is the new user case, which is where the popular
        // fallback runs. It was the unfiltered path before.
        catalogue.findCandidates(new LinkedHashSet<>(), new HashSet<>(), false);

        assertFalse(sender.paths.isEmpty(), "the fallback still has to fetch something");
        assertTrue(sender.everyDiscoverPathHas("without_keywords"));
        assertTrue(sender.anyPathHas("sort_by=popularity.desc"));
    }

    @Test
    @DisplayName("allowing adult content stops the filtering")
    void nothingIsFilteredWhenAllowed() {
        given();

        catalogue.findCandidates(Set.of(ANIMATION), new HashSet<>(), true);

        assertFalse(sender.anyPathHas("without_keywords"));
    }

    @Test
    @DisplayName("several genres are joined the way TMDB reads as any-of")
    void severalGenresAreJoinedEncoded() {
        given();

        catalogue.findCandidates(new LinkedHashSet<>(List.of(ANIMATION, COMEDY)),
                new HashSet<>(), false);

        // A raw bar is not legal in a URL and used to throw before it was encoded.
        assertTrue(sender.anyPathHas("%7C"), "genre ids are joined with an encoded bar");
        assertFalse(sender.anyPathHas("with_genres=16|35"));
    }

    @Test
    @DisplayName("the poster path is carried out of the response")
    void posterPathSurvives() {
        given();

        final List<Media> candidates =
                catalogue.findCandidates(Set.of(ANIMATION), new HashSet<>(), false);

        assertFalse(candidates.isEmpty());
        assertEquals("/abc.jpg", candidates.get(0).getPosterPath(),
                "without this the thumbnails have nothing to draw");
    }

    @Test
    @DisplayName("titles the user has already seen are dropped")
    void excludedIdsAreRemoved() {
        given();

        final List<Media> candidates =
                catalogue.findCandidates(Set.of(ANIMATION), new HashSet<>(Set.of(42)), false);

        assertTrue(candidates.isEmpty(), "id 42 was excluded, and it is all the source returned");
    }

    @Test
    @DisplayName("both films and shows are asked for")
    void filmsAndShowsAreBothFetched() {
        given();

        catalogue.findCandidates(Set.of(ANIMATION), new HashSet<>(), false);

        assertTrue(sender.anyPathHas("/discover/movie"));
        assertTrue(sender.anyPathHas("/discover/tv"));
    }
}
