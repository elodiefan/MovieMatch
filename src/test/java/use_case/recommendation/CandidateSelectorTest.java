package use_case.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Media;
import entity.Movie;
import entity.recommendation.TasteProfile;

/**
 * Tests what the selector asks the catalogue for.
 */
class CandidateSelectorTest {

    private static final Genre SCI_FI = new Genre(878, "Science Fiction");

    /**
     * Records the request rather than answering it properly.
     */
    private static final class RecordingCatalogue implements MediaCatalogueDataAccessInterface {
        private Set<Genre> askedGenres;
        private Set<Integer> askedExclusions;
        private Boolean askedForAdultContent;

        @Override
        public List<Media> findCandidates(Set<Genre> genres, Set<Integer> excludeMediaIds,
                                          boolean allowAdultContent) {
            this.askedGenres = genres;
            this.askedExclusions = excludeMediaIds;
            this.askedForAdultContent = allowAdultContent;
            return List.of(new Movie(1, "Something", 2020, 7.0,
                    List.of(SCI_FI), "en", new ArrayList<>(), 100));
        }

        @Override
        public Media findById(int mediaId) {
            return null;
        }
    }

    private RecordingCatalogue catalogue;
    private CandidateSelector selector;

    @BeforeEach
    void setUp() {
        catalogue = new RecordingCatalogue();
        selector = new CandidateSelector(catalogue);
    }

    private static TasteProfile sciFiFan() {
        return new TasteProfile(Set.of(SCI_FI), Set.of());
    }

    @Test
    @DisplayName("the content setting is passed to the source, not applied afterwards")
    void adultPreferenceReachesTheCatalogue() {
        // Only the source knows what counts as adult, so it has to be told.
        selector.selectFor(sciFiFan(), new ArrayList<>(), false);
        assertFalse(catalogue.askedForAdultContent);

        selector.selectFor(sciFiFan(), new ArrayList<>(), true);
        assertTrue(catalogue.askedForAdultContent);
    }

    @Test
    @DisplayName("titles the user already rated are excluded at the source")
    void ratedTitlesAreExcluded() {
        selector.selectFor(sciFiFan(),
                List.of(new UserRating(11, 5.0), new UserRating(12, 2.0)), false);

        assertTrue(catalogue.askedExclusions.contains(11));
        assertTrue(catalogue.askedExclusions.contains(12),
                "a title rated badly is still one the user has seen");
        assertEquals(2, catalogue.askedExclusions.size());
    }

    @Test
    @DisplayName("the profile's genres are what gets searched")
    void genresComeFromTheProfile() {
        selector.selectFor(sciFiFan(), new ArrayList<>(), false);

        assertEquals(Set.of(SCI_FI), catalogue.askedGenres);
    }

    @Test
    @DisplayName("a user who has rated nothing excludes nothing")
    void noRatingsMeansNoExclusions() {
        selector.selectFor(sciFiFan(), new ArrayList<>(), false);

        assertTrue(catalogue.askedExclusions.isEmpty());
    }

    @Test
    @DisplayName("the same title rated twice is only excluded once")
    void duplicateRatingsCollapse() {
        selector.selectFor(sciFiFan(),
                List.of(new UserRating(11, 5.0), new UserRating(11, 4.0)), false);

        assertEquals(1, catalogue.askedExclusions.size());
    }

    @Test
    @DisplayName("whatever the catalogue returns is what comes back")
    void resultsArePassedThrough() {
        final List<Media> candidates =
                selector.selectFor(sciFiFan(), new ArrayList<>(), false);

        assertEquals(1, candidates.size());
        assertEquals("Something", candidates.get(0).getTitle());
    }
}
