package use_case.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Movie;
import entity.recommendation.ScoredMedia;
import entity.recommendation.SubScoreBreakdown;
import entity.recommendation.TasteProfile;

/**
 * Tests turning a scored entity into what a screen draws.
 */
class RecommendedMediaMapperTest {

    private static final Genre SCI_FI = new Genre(878, "Science Fiction");
    private static final Genre DRAMA = new Genre(18, "Drama");
    private static final String POSTER = "/poster.jpg";

    private final RecommendedMediaMapper mapper = new RecommendedMediaMapper();

    private static SubScoreBreakdown emptyBreakdown() {
        return new SubScoreBreakdown(new LinkedHashMap<>(), new LinkedHashMap<>());
    }

    private static Movie movie(List<Genre> genres, String posterPath) {
        return new Movie(7, "Arrival", 2016, 7.6, genres, "en",
                new ArrayList<>(), 116, "words", posterPath);
    }

    private static TasteProfile profileLiking(Genre... genres) {
        return new TasteProfile(Set.of(genres), Set.of());
    }

    @Test
    @DisplayName("the poster path reaches the screen")
    void posterPathIsCarriedThrough() {
        // This is the step that used to drop it, which left every row blank.
        final ScoredMedia scored = new ScoredMedia(
                movie(List.of(SCI_FI), POSTER), 0.8, emptyBreakdown());

        final RecommendedMedia mapped = mapper.toRecommendedMedia(scored, profileLiking(SCI_FI));

        assertEquals(POSTER, mapped.getPosterPath());
    }

    @Test
    @DisplayName("a title with no artwork maps to no artwork, not to a broken path")
    void missingPosterStaysEmpty() {
        final ScoredMedia scored = new ScoredMedia(
                movie(List.of(SCI_FI), ""), 0.8, emptyBreakdown());

        final RecommendedMedia mapped = mapper.toRecommendedMedia(scored, profileLiking(SCI_FI));

        assertTrue(mapped.getPosterPath() == null || mapped.getPosterPath().isEmpty());
    }

    @Test
    @DisplayName("the title is filed under a genre the viewer actually likes")
    void primaryGenrePrefersTheViewersTaste() {
        // Drama comes first on the film, but sci-fi is what the viewer likes,
        // so that is the more useful heading to group it under.
        final ScoredMedia scored = new ScoredMedia(
                movie(List.of(DRAMA, SCI_FI), POSTER), 0.8, emptyBreakdown());

        final RecommendedMedia mapped = mapper.toRecommendedMedia(scored, profileLiking(SCI_FI));

        assertEquals("Science Fiction", mapped.getPrimaryGenre());
    }

    @Test
    @DisplayName("with no genre in common it still gets a heading")
    void fallsBackToTheFirstGenre() {
        final ScoredMedia scored = new ScoredMedia(
                movie(List.of(DRAMA), POSTER), 0.8, emptyBreakdown());

        final RecommendedMedia mapped = mapper.toRecommendedMedia(scored, profileLiking(SCI_FI));

        assertEquals("Drama", mapped.getPrimaryGenre());
    }

    @Test
    @DisplayName("a title with no genres at all is filed under Other")
    void noGenresAtAll() {
        final ScoredMedia scored = new ScoredMedia(
                movie(new ArrayList<>(), POSTER), 0.8, emptyBreakdown());

        final RecommendedMedia mapped = mapper.toRecommendedMedia(scored, profileLiking(SCI_FI));

        assertEquals("Other", mapped.getPrimaryGenre());
    }

    @Test
    @DisplayName("the rest of the title survives the mapping")
    void everythingElseIsCarried() {
        final ScoredMedia scored = new ScoredMedia(
                movie(List.of(SCI_FI), POSTER), 0.83, emptyBreakdown(), "because you like sci-fi");

        final RecommendedMedia mapped = mapper.toRecommendedMedia(scored, profileLiking(SCI_FI));

        assertEquals(7, mapped.getMediaId());
        assertEquals("Arrival", mapped.getTitle());
        assertEquals(2016, mapped.getReleaseYear());
        assertEquals(0.83, mapped.getScore(), 0.0001);
        assertEquals("because you like sci-fi", mapped.getExplanation());
    }

    @Test
    @DisplayName("mapping a list keeps the order it was given")
    void listMappingKeepsOrder() {
        final ScoredMedia first = new ScoredMedia(
                movie(List.of(SCI_FI), "/one.jpg"), 0.9, emptyBreakdown());
        final ScoredMedia second = new ScoredMedia(
                movie(List.of(SCI_FI), "/two.jpg"), 0.5, emptyBreakdown());

        final List<RecommendedMedia> mapped = mapper.toRecommendedMedia(
                List.of(first, second), profileLiking(SCI_FI));

        assertEquals(2, mapped.size());
        assertEquals("/one.jpg", mapped.get(0).getPosterPath());
        assertEquals("/two.jpg", mapped.get(1).getPosterPath());
    }

    @Test
    @DisplayName("an empty list maps to an empty list")
    void emptyListIsFine() {
        assertTrue(mapper.toRecommendedMedia(new ArrayList<>(), profileLiking(SCI_FI)).isEmpty());
    }
}
