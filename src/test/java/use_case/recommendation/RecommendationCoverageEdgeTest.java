package use_case.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Movie;
import entity.recommendation.RecommendationRanker;
import entity.recommendation.ScoredMedia;
import entity.recommendation.SubScoreBreakdown;
import entity.recommendation.TasteProfile;

class RecommendationCoverageEdgeTest {

    private static final SubScoreBreakdown EMPTY_BREAKDOWN =
            new SubScoreBreakdown(Map.of(), Map.of());

    @Test
    void rankerReturnsEmptyForZeroAndNegativeLimits() {
        final List<ScoredMedia> scored = List.of(scored(0.8));

        assertTrue(new RecommendationRanker().rank(scored, 0).isEmpty());
        assertTrue(new RecommendationRanker().rank(scored, -1).isEmpty());
    }

    @Test
    void breakdownFindsStrongestFactorAndHandlesNoFactors() {
        final Map<String, Double> weighted = new LinkedHashMap<>();
        weighted.put("genre", 0.2);
        weighted.put("cast", 0.4);

        assertEquals("cast", new SubScoreBreakdown(Map.of(), weighted).getStrongestFactor());
        assertEquals("", EMPTY_BREAKDOWN.getStrongestFactor());
        assertEquals(weighted, new SubScoreBreakdown(Map.of(), weighted).getWeightedScores());
        assertThrows(UnsupportedOperationException.class,
                () -> EMPTY_BREAKDOWN.getRawScores().put("x", 1.0));
    }

    @Test
    void tasteProfileIsEmptyOnlyWhenBothCollectionsAreEmpty() {
        assertTrue(new TasteProfile(Set.of(), Set.of()).isEmpty());
        assertTrue(!new TasteProfile(Set.of(new Genre(1, "Drama")), Set.of()).isEmpty());
        assertTrue(!new TasteProfile(Set.of(), Set.of("Actor")).isEmpty());
    }

    @Test
    void scoreAdjustmentApplierHandlesExceptionsShortListsNullsAndClamps() {
        final TasteProfile profile = new TasteProfile(Set.of(), Set.of());
        final List<ScoredMedia> shortlist = List.of(scored(0.5), scored(0.4), scored(0.3));
        final ScoreAdjuster oddAdjuster = new ScoreAdjuster() {
            @Override
            public Adjustment adjust(entity.Media candidate, TasteProfile tasteProfile) {
                return Adjustment.NONE;
            }

            @Override
            public List<Adjustment> adjustAll(List<entity.Media> candidates,
                                              TasteProfile tasteProfile) {
                final List<Adjustment> result = new ArrayList<>();
                result.add(new Adjustment(9.0, "clamped"));
                result.add(null);
                return result;
            }
        };

        final List<ScoredMedia> result = new ScoreAdjustmentApplier(oddAdjuster)
                .applyTo(shortlist, profile);

        assertEquals(0.55, result.get(0).getScore(), 0.0001);
        assertEquals(0.4, result.get(1).getScore(), 0.0001);
        assertEquals(0.3, result.get(2).getScore(), 0.0001);

        final ScoreAdjuster failing = (candidate, tasteProfile) -> Adjustment.NONE;
        final ScoreAdjuster batchFailing = new ScoreAdjuster() {
            @Override
            public Adjustment adjust(entity.Media candidate, TasteProfile tasteProfile) {
                return failing.adjust(candidate, tasteProfile);
            }

            @Override
            public List<Adjustment> adjustAll(List<entity.Media> candidates,
                                              TasteProfile tasteProfile) {
                throw new ScoreAdjustmentException("failed");
            }
        };
        final List<ScoredMedia> unchanged = new ScoreAdjustmentApplier(batchFailing)
                .applyTo(shortlist, profile);
        assertEquals(shortlist.get(0).getScore(), unchanged.get(0).getScore());
    }

    @Test
    void exceptionConstructorsPreserveMessageAndCause() {
        final RuntimeException cause = new RuntimeException("root");
        assertEquals("plain", new ScoreAdjustmentException("plain").getMessage());
        final ScoreAdjustmentException wrapped = new ScoreAdjustmentException("wrapped", cause);
        assertEquals("wrapped", wrapped.getMessage());
        assertSame(cause, wrapped.getCause());
    }

    @Test
    void genreSectionExposesHeadingAndImmutableRecommendations() {
        final GenreSection section = new GenreSection("Drama", List.of());
        assertEquals("Drama", section.getGenreName());
        assertEquals("Because you like Drama", section.getHeading());
        assertThrows(UnsupportedOperationException.class,
                () -> section.getRecommendations().add(null));
    }

    private static ScoredMedia scored(double score) {
        final Movie movie = new Movie((int) (score * 1000), "Movie", 2024, 8.0,
                new ArrayList<>(), "en", new ArrayList<>(), 100);
        return new ScoredMedia(movie, score, EMPTY_BREAKDOWN);
    }
}
