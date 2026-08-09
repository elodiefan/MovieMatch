package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Movie;
import entity.recommendation.TasteProfile;
import use_case.recommendation.Adjustment;
import use_case.recommendation.ScoreAdjustmentException;

class GeminiScoreAdjusterTest {

    private static final TasteProfile EMPTY_PROFILE = new TasteProfile(Set.of(), Set.of());

    @Test
    void configurationRequiresANonBlankKey() {
        assertFalse(new GeminiScoreAdjuster(null, null).isConfigured());
        assertFalse(new GeminiScoreAdjuster("  ", "").isConfigured());
        assertTrue(new GeminiScoreAdjuster("key", null).isConfigured());
    }

    @Test
    void singleAdjustmentWithoutAKeyFailsBeforeAnyNetworkCall() {
        final GeminiScoreAdjuster adjuster = new GeminiScoreAdjuster(null, null);

        final ScoreAdjustmentException error = assertThrows(ScoreAdjustmentException.class,
                () -> adjuster.adjust(movie(1), EMPTY_PROFILE));

        assertTrue(error.getMessage().contains(GeminiScoreAdjuster.API_KEY_VARIABLE));
    }

    @Test
    void batchWithoutAKeyReturnsOneNoOpPerCandidate() {
        final GeminiScoreAdjuster adjuster = new GeminiScoreAdjuster(null, null);

        final List<Adjustment> adjustments =
                adjuster.adjustAll(List.of(movie(1), movie(2)), EMPTY_PROFILE);

        assertEquals(2, adjustments.size());
        assertEquals(0.0, adjustments.get(0).getDelta());
        assertEquals(0.0, adjustments.get(1).getDelta());
    }

    @Test
    void anEmptyBatchNeedsNoNetworkRequest() {
        final GeminiScoreAdjuster adjuster = new GeminiScoreAdjuster("key", null);

        assertTrue(adjuster.adjustAll(List.of(), EMPTY_PROFILE).isEmpty());
    }

    private static Movie movie(int id) {
        return new Movie(id, "Movie " + id, 2024, 8.0,
                new ArrayList<Genre>(), "en", new ArrayList<>(), 100);
    }
}
