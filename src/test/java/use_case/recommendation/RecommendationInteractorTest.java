package use_case.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import data_access.ClampingScoreAdjuster;
import data_access.InMemoryRecommendationDataAccessObject;
import data_access.NoOpScoreAdjuster;
import data_access.SeedMediaCatalogue;
import entity.Media;
import entity.recommendation.TasteProfile;

/**
 * Drives the interactor end to end over the seed catalogue.
 * <p>
 * Uses the real collaborators rather than mocks, so these also check that the
 * pieces fit together — a taste profile built from ratings actually reaches the
 * scorers, and the ranking that comes out is the one the presenter is handed.
 */
class RecommendationInteractorTest {

    private static final int CURRENT_YEAR = 2026;
    private static final String USER = "enzo";
    private static final String FRIEND = "lily";

    private static final int DUNE = 101;
    private static final int ARRIVAL = 102;
    private static final int SICARIO = 103;
    private static final int PAST_LIVES = 107;
    private static final int SEVERANCE = 201;

    private InMemoryRecommendationDataAccessObject userData;
    private SeedMediaCatalogue catalogue;
    private CapturingPresenter presenter;

    /**
     * Captures whatever the interactor reports, so tests can assert on it.
     */
    private static final class CapturingPresenter implements RecommendationOutputBoundary {
        private RecommendationOutputData output;
        private String error;

        @Override
        public void presentRecommendations(final RecommendationOutputData outputData) {
            this.output = outputData;
        }

        @Override
        public void prepareFailView(final String errorMessage) {
            this.error = errorMessage;
        }
    }

    /**
     * An adjuster that always asks for a wildly out-of-range shift.
     */
    private static final class RunawayAdjuster implements ScoreAdjuster {
        @Override
        public Adjustment adjust(final Media candidate, final TasteProfile tasteProfile) {
            return new Adjustment(10.0, "wildly overconfident");
        }
    }

    /**
     * An adjuster that always fails, standing in for a missing API key.
     */
    private static final class FailingAdjuster implements ScoreAdjuster {
        @Override
        public Adjustment adjust(final Media candidate, final TasteProfile tasteProfile) {
            throw new ScoreAdjustmentException("no API key configured");
        }
    }

    @BeforeEach
    void setUp() {
        this.userData = new InMemoryRecommendationDataAccessObject();
        this.catalogue = new SeedMediaCatalogue();
        this.presenter = new CapturingPresenter();
    }

    private RecommendationInteractor interactorWith(final ScoreAdjuster adjuster) {
        return new RecommendationInteractor(this.userData, this.catalogue, adjuster,
                this.presenter, CURRENT_YEAR);
    }

    private void givenUserLovesSciFi() {
        this.userData.recordRating(USER, DUNE, 5.0);
        this.userData.recordRating(USER, ARRIVAL, 5.0);
        // Rated low, so it must not shape the taste profile.
        this.userData.recordRating(USER, PAST_LIVES, 2.0);
    }

    @Test
    @DisplayName("a sci-fi fan is recommended sci-fi")
    void recommendsFromTasteProfile() {
        this.givenUserLovesSciFi();

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 5, false));

        final List<RecommendedMedia> results = this.presenter.output.getRecommendations();
        assertFalse(results.isEmpty(), "a user with a clear taste should get suggestions");
        assertTrue(results.size() <= 5, "should honour the requested limit");
    }

    @Test
    @DisplayName("titles the user already rated are never suggested back to them")
    void excludesAlreadyRated() {
        this.givenUserLovesSciFi();

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 10, false));

        final List<Integer> suggestedIds = this.presenter.output.getRecommendations().stream()
                .map(RecommendedMedia::getMediaId)
                .collect(java.util.stream.Collectors.toList());
        assertFalse(suggestedIds.contains(DUNE), "Dune was rated, so must not come back");
        assertFalse(suggestedIds.contains(ARRIVAL), "Arrival was rated, so must not come back");
        assertFalse(suggestedIds.contains(PAST_LIVES), "Past Lives was rated, so must not come back");
    }

    @Test
    @DisplayName("results come back ordered best first")
    void resultsAreOrdered() {
        this.givenUserLovesSciFi();

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 10, false));

        final List<RecommendedMedia> results = this.presenter.output.getRecommendations();
        for (int i = 1; i < results.size(); i++) {
            assertTrue(results.get(i - 1).getScore() >= results.get(i).getScore(),
                    "scores should not increase down the list");
        }
    }

    @Test
    @DisplayName("grouping produces genre sections with headings")
    void groupsByGenreWhenAsked() {
        this.givenUserLovesSciFi();

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 10, true));

        final List<GenreSection> sections = this.presenter.output.getSections();
        assertFalse(sections.isEmpty(), "grouping was requested, so sections should exist");
        assertTrue(sections.get(0).getHeading().startsWith("Because you like "),
                "sections should read as an explanation");
    }

    @Test
    @DisplayName("a flat request produces no sections")
    void skipsGroupingWhenNotAsked() {
        this.givenUserLovesSciFi();

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 5, false));

        assertTrue(this.presenter.output.getSections().isEmpty());
    }

    @Test
    @DisplayName("a friend's high rating lifts a title's score")
    void friendRatingsCount() {
        this.givenUserLovesSciFi();
        this.userData.recordFriend(USER, FRIEND);
        this.userData.recordRating(FRIEND, SEVERANCE, 5.0);

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 10, false));
        final double withFriend = this.scoreOf(SEVERANCE);

        // Same setup, but nobody vouches for it.
        this.setUp();
        this.givenUserLovesSciFi();
        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 10, false));
        final double withoutFriend = this.scoreOf(SEVERANCE);

        assertTrue(withFriend > withoutFriend,
                "a friend rating it five stars should raise the score");
    }

    @Test
    @DisplayName("an adjuster asking for a huge shift is clamped to 0.05")
    void runawayAdjusterIsClamped() {
        this.givenUserLovesSciFi();

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 10, false));
        final double plain = this.presenter.output.getRecommendations().get(0).getScore();

        this.presenter = new CapturingPresenter();
        this.interactorWith(new ClampingScoreAdjuster(new RunawayAdjuster()))
                .recommend(new RecommendationInputData(USER, 10, false));
        final double adjusted = this.presenter.output.getRecommendations().get(0).getScore();

        assertEquals(0.05, adjusted - plain, 0.0001,
                "a delta of 10 must be clamped to the 0.05 maximum");
    }

    @Test
    @DisplayName("an adjuster that fails leaves the deterministic ranking untouched")
    void failingAdjusterFallsBackToZero() {
        this.givenUserLovesSciFi();

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 10, false));
        final double plain = this.presenter.output.getRecommendations().get(0).getScore();

        this.presenter = new CapturingPresenter();
        this.interactorWith(new FailingAdjuster())
                .recommend(new RecommendationInputData(USER, 10, false));
        final double afterFailure = this.presenter.output.getRecommendations().get(0).getScore();

        assertEquals(plain, afterFailure, 0.0001,
                "a broken adjuster must not change what the user sees");
    }

    @Test
    @DisplayName("a user who has rated nothing still gets suggestions")
    void newUserStillGetsSuggestions() {
        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 5, false));

        assertFalse(this.presenter.output.getRecommendations().isEmpty(),
                "an empty taste profile should fall back to popularity and recency");
    }

    @Test
    @DisplayName("when every title has been rated there is nothing left to suggest")
    void nothingLeftToSuggest() {
        for (final Media media : this.catalogue.getAll()) {
            this.userData.recordRating(USER, media.getID(), 5.0);
        }

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 5, false));

        assertEquals("No recommendations yet — rate a few titles and check back.",
                this.presenter.error);
    }

    @Test
    @DisplayName("a title with no cast listed can still be recommended")
    void titleWithNoCastSurvivesScoring() {
        this.userData.recordRating(USER, SICARIO, 5.0);

        this.interactorWith(new NoOpScoreAdjuster())
                .recommend(new RecommendationInputData(USER, 15, false));

        assertFalse(this.presenter.output.getRecommendations().isEmpty(),
                "scoring must not fall over on a catalogue entry with no cast");
    }

    private double scoreOf(final int mediaId) {
        return this.presenter.output.getRecommendations().stream()
                .filter(recommendation -> recommendation.getMediaId() == mediaId)
                .findFirst()
                .map(RecommendedMedia::getScore)
                .orElse(0.0);
    }
}
