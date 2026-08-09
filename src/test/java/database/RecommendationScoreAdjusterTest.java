package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Media;
import entity.Movie;
import entity.recommendation.TasteProfile;
import use_case.recommendation.Adjustment;
import use_case.recommendation.ScoreAdjuster;

class RecommendationScoreAdjusterTest {

    private static final TasteProfile PROFILE = new TasteProfile(Set.of(), Set.of());

    @Test
    void noOpAlwaysReturnsTheSharedNoAdjustment() {
        assertSame(Adjustment.NONE, new NoOpScoreAdjuster().adjust(movie(1), PROFILE));
        assertEquals(List.of(Adjustment.NONE, Adjustment.NONE),
                new NoOpScoreAdjuster().adjustAll(List.of(movie(1), movie(2)), PROFILE));
    }

    @Test
    void clampingAdjusterCapsPositiveAndNegativeSingleAdjustments() {
        final ScoreAdjuster positive = (candidate, profile) -> new Adjustment(2.0, "up");
        final ScoreAdjuster negative = (candidate, profile) -> new Adjustment(-2.0, "down");

        final Adjustment up = new ClampingScoreAdjuster(positive).adjust(movie(1), PROFILE);
        final Adjustment down = new ClampingScoreAdjuster(negative).adjust(movie(1), PROFILE);

        assertEquals(ClampingScoreAdjuster.MAX_ADJUSTMENT, up.getDelta());
        assertEquals(-ClampingScoreAdjuster.MAX_ADJUSTMENT, down.getDelta());
        assertEquals("up", up.getExplanation());
        assertEquals("down", down.getExplanation());
    }

    @Test
    void batchClampsEveryDelegateResultAndPreservesOrder() {
        final ScoreAdjuster delegate = new ScoreAdjuster() {
            @Override
            public Adjustment adjust(Media candidate, TasteProfile profile) {
                return Adjustment.NONE;
            }

            @Override
            public List<Adjustment> adjustAll(List<Media> candidates, TasteProfile profile) {
                return List.of(new Adjustment(0.01, "first"),
                        new Adjustment(0.5, "second"), new Adjustment(-0.5, "third"));
            }
        };

        final List<Adjustment> adjusted = new ClampingScoreAdjuster(delegate)
                .adjustAll(List.of(movie(1), movie(2), movie(3)), PROFILE);

        assertEquals(List.of(0.01, 0.05, -0.05),
                adjusted.stream().map(Adjustment::getDelta).collect(Collectors.toList()));
        assertEquals("second", adjusted.get(1).getExplanation());
    }

    private static Movie movie(int id) {
        return new Movie(id, "Movie " + id, 2024, 8.0,
                new ArrayList<Genre>(), "en", new ArrayList<>(), 100);
    }
}
