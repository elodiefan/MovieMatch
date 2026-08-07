package entity.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Movie;

/**
 * Covers the cases real data produces that the worked example does not: missing
 * cast lists, users with no friends, titles older than the recency window, and
 * misconfigured weights.
 */
class ScoringEdgeCaseTest {

    private static final int CURRENT_YEAR = 2026;
    private static final double TOLERANCE = 0.001;
    private static final String ENGLISH = "en";
    private static final int RUNTIME = 100;

    private static final Genre SCI_FI = new Genre(878, "Sci-Fi");
    private static final Genre HORROR = new Genre(27, "Horror");

    private ScoringContext contextWith(final TasteProfile profile, final List<Double> friendRatings) {
        return new ScoringContext(profile, friendRatings, CURRENT_YEAR, ScoringWeights.createDefault());
    }

    private TasteProfile sciFiProfile() {
        return new TasteProfile(new HashSet<>(Collections.singletonList(SCI_FI)),
                new HashSet<>(Collections.singletonList("Actor1")));
    }

    @Test
    @DisplayName("a title with no cast listed scores zero rather than dividing by zero")
    void emptyCastDoesNotDivideByZero() {
        final Movie noCast = new Movie(1, "No Cast", 2024, 7.0,
                Collections.singletonList(SCI_FI), ENGLISH, new ArrayList<>(), RUNTIME);
        final double score = new CastOverlapSubScore()
                .scoreFor(noCast, contextWith(sciFiProfile(), new ArrayList<>()));
        assertEquals(0.0, score, TOLERANCE);
    }

    @Test
    @DisplayName("a title with no genres listed scores zero rather than dividing by zero")
    void emptyGenresDoesNotDivideByZero() {
        final Movie noGenres = new Movie(2, "No Genres", 2024, 7.0,
                new ArrayList<>(), ENGLISH, Collections.singletonList("Actor1"), RUNTIME);
        final double score = new GenreOverlapSubScore()
                .scoreFor(noGenres, contextWith(sciFiProfile(), new ArrayList<>()));
        assertEquals(0.0, score, TOLERANCE);
    }

    @Test
    @DisplayName("a user with no friends scores zero on the friends factor")
    void noFriendsScoresZero() {
        final Movie any = new Movie(3, "Any", 2024, 7.0,
                Collections.singletonList(SCI_FI), ENGLISH, Collections.singletonList("Actor1"), RUNTIME);
        final double score = new FriendRatingSubScore()
                .scoreFor(any, contextWith(sciFiProfile(), new ArrayList<>()));
        assertEquals(0.0, score, TOLERANCE);
    }

    @Test
    @DisplayName("a title older than the recency window floors at zero, not negative")
    void ancientTitleFloorsAtZero() {
        final Movie ancient = new Movie(4, "Ancient", 1970, 8.0,
                Collections.singletonList(SCI_FI), ENGLISH, Collections.singletonList("Actor1"), RUNTIME);
        final double score = new RecencySubScore()
                .scoreFor(ancient, contextWith(sciFiProfile(), new ArrayList<>()));
        assertEquals(0.0, score, TOLERANCE);
    }

    @Test
    @DisplayName("an unreleased title caps at one rather than exceeding it")
    void futureTitleCapsAtOne() {
        final Movie future = new Movie(5, "Future", 2030, 8.0,
                Collections.singletonList(SCI_FI), ENGLISH, Collections.singletonList("Actor1"), RUNTIME);
        final double score = new RecencySubScore()
                .scoreFor(future, contextWith(sciFiProfile(), new ArrayList<>()));
        assertEquals(1.0, score, TOLERANCE);
    }

    @Test
    @DisplayName("an out-of-range source rating cannot push popularity above one")
    void popularityClampsToOne() {
        final Movie overrated = new Movie(6, "Overrated", 2024, 99.0,
                Collections.singletonList(SCI_FI), ENGLISH, Collections.singletonList("Actor1"), RUNTIME);
        final double score = new PopularitySubScore()
                .scoreFor(overrated, contextWith(sciFiProfile(), new ArrayList<>()));
        assertEquals(1.0, score, TOLERANCE);
    }

    @Test
    @DisplayName("a brand-new user with an empty profile still gets scored on popularity and recency")
    void emptyProfileStillProducesAScore() {
        final TasteProfile empty = new TasteProfile(new HashSet<>(), new HashSet<>());
        final Movie candidate = new Movie(7, "Candidate", 2026, 10.0,
                Collections.singletonList(HORROR), ENGLISH, Collections.singletonList("Actor9"), RUNTIME);
        final ScoredMedia result = WeightedScoreCalculator.createDefault()
                .score(candidate, contextWith(empty, new ArrayList<>()));
        assertTrue(empty.isEmpty(), "profile should report itself empty");
        // Popularity 1.0 x 0.15, recency 1.0 x 0.10, everything else zero.
        assertEquals(0.25, result.getScore(), TOLERANCE);
    }

    @Test
    @DisplayName("weights that do not sum to one are rejected at construction")
    void badWeightsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new ScoringWeights(0.5, 0.5, 0.5, 0.5, 0.5, 15));
    }

    @Test
    @DisplayName("genres compare by value, so separately loaded copies still overlap")
    void genresCompareByValue() {
        final Genre loadedElsewhere = new Genre(878, "Sci-Fi");
        final Movie candidate = new Movie(8, "Candidate", 2024, 7.0,
                Collections.singletonList(loadedElsewhere), ENGLISH,
                Collections.singletonList("Actor1"), RUNTIME);
        final double score = new GenreOverlapSubScore()
                .scoreFor(candidate, contextWith(sciFiProfile(), new ArrayList<>()));
        assertEquals(1.0, score, TOLERANCE,
                "a distinct Genre object with the same id must still count as a match");
    }

    @Test
    @DisplayName("the ranker orders by score descending and honours the limit")
    void rankerSortsAndLimits() {
        final SubScoreBreakdown breakdown =
                new SubScoreBreakdown(Collections.emptyMap(), Collections.emptyMap());
        final Movie any = new Movie(9, "Any", 2024, 7.0,
                Collections.singletonList(SCI_FI), ENGLISH, Collections.singletonList("Actor1"), RUNTIME);
        final List<ScoredMedia> unordered = Arrays.asList(
                new ScoredMedia(any, 0.20, breakdown),
                new ScoredMedia(any, 0.90, breakdown),
                new ScoredMedia(any, 0.55, breakdown));

        final List<ScoredMedia> ranked = new RecommendationRanker().rank(unordered, 2);

        assertEquals(2, ranked.size());
        assertEquals(0.90, ranked.get(0).getScore(), TOLERANCE);
        assertEquals(0.55, ranked.get(1).getScore(), TOLERANCE);
    }
}
