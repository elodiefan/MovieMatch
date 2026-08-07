package entity.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import entity.Genre;
import entity.Movie;

/**
 * Checks the scoring against the worked example in section 6 of
 * {@code Recommendation_Algorithm.pdf}.
 *
 * The document states every intermediate value, so each factor is asserted on
 * its own before the weighted total. A failure therefore names the factor that
 * drifted instead of only reporting that the total was wrong.
 */
class WorkedExampleTest {

    /** The document computes recency as at 2026. */
    private static final int CURRENT_YEAR = 2026;

    private static final double TOLERANCE = 0.001;

    private static final Genre ACTION = new Genre(28, "Action");
    private static final Genre SCI_FI = new Genre(878, "Sci-Fi");
    private static final Genre THRILLER = new Genre(53, "Thriller");
    private static final Genre DRAMA = new Genre(18, "Drama");
    private static final Genre ADVENTURE = new Genre(12, "Adventure");

    private static final String ACTOR_1 = "Actor1";
    private static final String ACTOR_2 = "Actor2";
    private static final String ACTOR_3 = "Actor3";
    private static final String ACTOR_4 = "Actor4";

    private static final String ENGLISH = "en";
    private static final int RUNTIME = 120;

    private TasteProfile profile;
    private Movie candidateD;
    private ScoringContext context;

    @BeforeEach
    void setUp() {
        // Movie A - Action/Sci-Fi, 5 stars, released 2020.
        final Movie movieA = new Movie(1, "Movie A", 2020, 8.0,
                Arrays.asList(ACTION, SCI_FI), ENGLISH, Arrays.asList(ACTOR_1, ACTOR_2), RUNTIME);
        // Movie B - Drama, 3 stars. Below the threshold, so it must not count.
        final Movie movieB = new Movie(2, "Movie B", 2019, 7.0,
                Collections.singletonList(DRAMA), ENGLISH, Collections.singletonList("Actor9"), RUNTIME);
        // Movie C - Sci-Fi/Thriller, 4 stars, released 2018.
        final Movie movieC = new Movie(3, "Movie C", 2018, 7.5,
                Arrays.asList(SCI_FI, THRILLER), ENGLISH, Collections.singletonList(ACTOR_3), RUNTIME);

        this.profile = new TasteProfileBuilder()
                .add(movieA, 5.0)
                .add(movieB, 3.0)
                .add(movieC, 4.0)
                .build();

        // Candidate D - Sci-Fi/Adventure, TMDB 7.8, released 2024.
        this.candidateD = new Movie(4, "Movie D", 2024, 7.8,
                Arrays.asList(SCI_FI, ADVENTURE), ENGLISH, Arrays.asList(ACTOR_2, ACTOR_4), RUNTIME);

        // Two friends rated D at 5 and 4 stars.
        final List<Double> friendRatings = Arrays.asList(5.0, 4.0);
        this.context = new ScoringContext(this.profile, friendRatings, CURRENT_YEAR,
                ScoringWeights.createDefault());
    }

    @Test
    @DisplayName("section 2: three-star media is excluded from the taste profile")
    void tasteProfileExcludesLowRatings() {
        assertEquals(3, this.profile.getGenres().size(),
                "profile should hold Action, Sci-Fi and Thriller but not Drama");
        assertEquals(3, this.profile.getCast().size(),
                "profile should hold Actor1, Actor2 and Actor3 only");
    }

    @Test
    @DisplayName("G(D) = 0.50")
    void genreOverlapMatchesDocument() {
        assertEquals(0.50, new GenreOverlapSubScore().scoreFor(this.candidateD, this.context),
                TOLERANCE);
    }

    @Test
    @DisplayName("CastScore(D) = 0.50")
    void castOverlapMatchesDocument() {
        assertEquals(0.50, new CastOverlapSubScore().scoreFor(this.candidateD, this.context),
                TOLERANCE);
    }

    @Test
    @DisplayName("P(D) = 0.78")
    void popularityMatchesDocument() {
        assertEquals(0.78, new PopularitySubScore().scoreFor(this.candidateD, this.context),
                TOLERANCE);
    }

    @Test
    @DisplayName("FriendScore(D) = 0.90")
    void friendRatingMatchesDocument() {
        assertEquals(0.90, new FriendRatingSubScore().scoreFor(this.candidateD, this.context),
                TOLERANCE);
    }

    @Test
    @DisplayName("Recency(D) = 0.867")
    void recencyMatchesDocument() {
        assertEquals(0.867, new RecencySubScore().scoreFor(this.candidateD, this.context),
                TOLERANCE);
    }

    @Test
    @DisplayName("Score(D) = 0.639")
    void weightedTotalMatchesDocument() {
        final ScoredMedia result =
                WeightedScoreCalculator.createDefault().score(this.candidateD, this.context);
        assertEquals(0.639, result.getScore(), TOLERANCE);
    }

    @Test
    @DisplayName("the breakdown names genre as the strongest contributor")
    void breakdownIdentifiesStrongestFactor() {
        final ScoredMedia result =
                WeightedScoreCalculator.createDefault().score(this.candidateD, this.context);
        // 0.40 x 0.50 = 0.20, ahead of friends at 0.15 x 0.90 = 0.135.
        assertEquals("genre", result.getBreakdown().getStrongestFactor());
    }
}
