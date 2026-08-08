package use_case.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import entity.Media;
import entity.recommendation.RecommendationRanker;
import entity.recommendation.ScoredMedia;
import entity.recommendation.ScoringContext;
import entity.recommendation.ScoringWeights;
import entity.recommendation.TasteProfile;
import entity.recommendation.TasteProfileBuilder;
import entity.recommendation.WeightedScoreCalculator;

/**
 * Produces recommendations by running the seven steps of the algorithm.
 */
public class RecommendationInteractor implements RecommendationInputBoundary {

    /**
     * How many extra candidates beyond the limit are offered to the adjuster.
     */
    private static final int SHORTLIST_MARGIN = 5;

    private static final String NO_CANDIDATES =
            "No recommendations yet — rate a few titles and check back.";

    private final RecommendationDataAccessInterface userDataAccess;
    private final MediaCatalogueDataAccessInterface catalogue;
    private final CandidateSelector candidateSelector;
    private final WeightedScoreCalculator scoreCalculator;
    private final ScoreAdjustmentApplier adjustmentApplier;
    private final RecommendationRanker ranker;
    private final RecommendedMediaMapper mapper;
    private final GenreGrouper grouper;
    private final RecommendationOutputBoundary presenter;
    private final AdultContentPreferenceDataAccessInterface contentPreferences;
    private final ScoringWeights weights;
    private final int currentYear;

    /**
     * Creates an interactor that never offers adult titles.
     *
     * @param userDataAccess the user data access
     * @param catalogue the catalogue
     * @param adjuster the adjuster
     * @param presenter the presenter
     * @param currentYear the current year
     */
    public RecommendationInteractor(final RecommendationDataAccessInterface userDataAccess,
                                    final MediaCatalogueDataAccessInterface catalogue,
                                    final ScoreAdjuster adjuster,
                                    final RecommendationOutputBoundary presenter,
                                    final int currentYear) {
        this(userDataAccess, catalogue, adjuster, presenter, currentYear, () -> false);
    }

    /**
     * Creates an interactor.
     *
     * @param userDataAccess the user data access
     * @param catalogue the catalogue
     * @param adjuster the adjuster
     * @param presenter the presenter
     * @param currentYear the current year
     * @param contentPreferences what kind of titles the user wants offered
     */
    public RecommendationInteractor(final RecommendationDataAccessInterface userDataAccess,
                                    final MediaCatalogueDataAccessInterface catalogue,
                                    final ScoreAdjuster adjuster,
                                    final RecommendationOutputBoundary presenter,
                                    final int currentYear,
                                    final AdultContentPreferenceDataAccessInterface
                                            contentPreferences) {
        this.contentPreferences = contentPreferences;
        this.userDataAccess = userDataAccess;
        this.catalogue = catalogue;
        this.candidateSelector = new CandidateSelector(catalogue);
        this.scoreCalculator = WeightedScoreCalculator.createDefault();
        this.adjustmentApplier = new ScoreAdjustmentApplier(adjuster);
        this.ranker = new RecommendationRanker();
        this.mapper = new RecommendedMediaMapper();
        this.grouper = new GenreGrouper();
        this.presenter = presenter;
        this.weights = ScoringWeights.createDefault();
        this.currentYear = currentYear;
    }

    @Override
    public void recommend(final RecommendationInputData inputData) {
        final String username = inputData.getUsername();

        // Steps 1 and 2: turn the user's ratings into a taste profile.
        final List<UserRating> ratings = this.userDataAccess.findRatingsByUser(username);
        final TasteProfile profile = this.buildProfile(ratings);

        // Step 3: candidates are unwatched titles sharing the user's genres.
        final List<Media> candidates = this.candidateSelector.selectFor(
                profile, ratings, this.contentPreferences.isAdultContentAllowed());

        if (candidates.isEmpty()) {
            this.presenter.prepareFailView(NO_CANDIDATES);
        }
        else {
            this.presenter.presentRecommendations(
                    this.rankAndPresent(username, inputData, profile, candidates));
        }
    }

    /**
     * Turns the user's ratings into a taste profile, ignoring anything they rated below the threshold and anything the catalogue no longer holds.
     *
     * @param ratings the ratings
     * @return the build profile
     */
    private TasteProfile buildProfile(final List<UserRating> ratings) {
        final TasteProfileBuilder builder = new TasteProfileBuilder();
        for (final UserRating rating : ratings) {
            final Media rated = this.catalogue.findById(rating.getMediaId());
            if (rated != null) {
                builder.add(rated, rating.getRating());
            }
        }
        return builder.build();
    }

    /**
     * Scores, refines, ranks and packages the candidates.
     *
     * @param username the username
     * @param inputData the input data
     * @param profile the profile
     * @param candidates the candidates
     * @return the rank and present
     */
    private RecommendationOutputData rankAndPresent(final String username,
                                                    final RecommendationInputData inputData,
                                                    final TasteProfile profile,
                                                    final List<Media> candidates) {
        final List<String> friends = this.userDataAccess.findFriendUsernames(username);

        // Steps 4 and 5: score every candidate.
        final List<ScoredMedia> scored = new ArrayList<>();
        for (final Media candidate : candidates) {
            scored.add(this.scoreCalculator.score(candidate, this.contextFor(profile, friends, candidate)));
        }

        // Step 6, then the adjuster sees only a slightly longer shortlist than
        // will actually be shown, so weak candidates stay invisible to it.
        final List<ScoredMedia> shortlist =
                this.ranker.rank(scored, inputData.getLimit() + SHORTLIST_MARGIN);
        final List<ScoredMedia> refined = this.adjustmentApplier.applyTo(shortlist, profile);

        // Step 7: re-rank after adjustment, then take what was asked for.
        final List<ScoredMedia> finalRanking = this.ranker.rank(refined, inputData.getLimit());
        final List<RecommendedMedia> mapped = this.mapper.toRecommendedMedia(finalRanking, profile);

        List<GenreSection> sections = Collections.emptyList();
        if (inputData.isGroupByGenre()) {
            sections = this.grouper.group(mapped);
        }
        return new RecommendationOutputData(username, mapped, sections);
    }

    /**
     * Assembles what the scorers need to judge one candidate.
     *
     * @param profile the profile
     * @param friends the friends
     * @param candidate the candidate
     * @return the context for
     */
    private ScoringContext contextFor(final TasteProfile profile, final List<String> friends,
                                      final Media candidate) {
        final List<Double> friendRatings = this.userDataAccess
                .findFriendRatingsForMedia(friends, candidate.getID())
                .stream()
                .map(UserRating::getRating)
                .collect(Collectors.toList());
        return new ScoringContext(profile, friendRatings, this.currentYear, this.weights);
    }
}
