package app;

import java.time.Year;

import data_access.ClampingScoreAdjuster;
import data_access.GeminiScoreAdjuster;
import data_access.NoOpScoreAdjuster;
import data_access.TmdbApiClient;
import data_access.TmdbMediaCatalogue;
import data_access.UserActivityRecommendationDataAccess;
import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationPresenter;
import interface_adapter.recommendation.RecommendationViewModel;
import use_case.recommendation.MediaCatalogueDataAccessInterface;
import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInteractor;
import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.ScoreAdjuster;
import use_case.recommendation.WatchedMediaDataAccessInterface;
import use_case.review.ReviewDataAccessInterface;
import view.HomeRecommendationsPanel;
import view.RecommendationView;

/**
 * Assembles the recommendation use case.
 */
public final class RecommendationUseCaseFactory {

    private RecommendationUseCaseFactory() {
    }

    /**
     * Builds both recommendation screens over one interactor class.
     *
     * @param viewManagerModel the view manager model
     * @param homeStripViewModel the home strip view model
     * @param detailedViewModel the detailed view model
     * @param homeRecommendationsPanel the home recommendations panel
     * @param recommendationView the recommendation view
     * @param watchedMediaDataAccess the watched media data access
     * @param reviewDataAccess the review data access
     */
    public static void create(ViewManagerModel viewManagerModel,
                              RecommendationViewModel homeStripViewModel,
                              RecommendationViewModel detailedViewModel,
                              HomeRecommendationsPanel homeRecommendationsPanel,
                              RecommendationView recommendationView,
                              WatchedMediaDataAccessInterface watchedMediaDataAccess,
                              ReviewDataAccessInterface reviewDataAccess) {

        final TmdbApiClient tmdbApiClient = new TmdbApiClient();
        final MediaCatalogueDataAccessInterface catalogue = new TmdbMediaCatalogue(tmdbApiClient);
        final RecommendationDataAccessInterface userDataAccess =
                new UserActivityRecommendationDataAccess(watchedMediaDataAccess, reviewDataAccess);

        final ScoreAdjuster adjuster = chooseAdjuster();
        final int currentYear = Year.now().getValue();

        homeRecommendationsPanel.setRecommendationController(new RecommendationController(
                buildInteractor(userDataAccess, catalogue, adjuster,
                        new RecommendationPresenter(homeStripViewModel), currentYear),
                viewManagerModel, HomePageViewModel.VIEW_NAME));

        recommendationView.setRecommendationController(new RecommendationController(
                buildInteractor(userDataAccess, catalogue, adjuster,
                        new RecommendationPresenter(detailedViewModel), currentYear),
                viewManagerModel, HomePageViewModel.VIEW_NAME));
    }

    private static RecommendationInputBoundary buildInteractor(
            RecommendationDataAccessInterface userDataAccess,
            MediaCatalogueDataAccessInterface catalogue,
            ScoreAdjuster adjuster,
            RecommendationOutputBoundary presenter,
            int currentYear) {
        return new RecommendationInteractor(userDataAccess, catalogue, adjuster,
                presenter, currentYear);
    }

    /**
     * Uses Gemini when a key is configured, and nothing at all when it is not.
     *
     * @return the choose adjuster
     */
    private static ScoreAdjuster chooseAdjuster() {
        final GeminiScoreAdjuster gemini = new GeminiScoreAdjuster();
        final ScoreAdjuster chosen;
        if (gemini.isConfigured()) {
            chosen = new ClampingScoreAdjuster(gemini);
        }
        else {
            chosen = new NoOpScoreAdjuster();
        }
        return chosen;
    }
}
