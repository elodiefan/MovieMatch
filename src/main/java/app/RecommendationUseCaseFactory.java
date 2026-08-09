package app;

import java.time.Year;
import java.util.concurrent.Executor;

import database.ClampingScoreAdjuster;
import database.GeminiScoreAdjuster;
import database.NoOpScoreAdjuster;
import database.TMDBAPIClient;
import database.TMDBMediaCatalogue;
import database.UserActivityRecommendationDataAccess;
import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationPresenter;
import interface_adapter.recommendation.RecommendationViewModel;
import use_case.recommendation.AdultContentPreferenceDataAccessInterface;
import use_case.recommendation.MediaCatalogueDataAccessInterface;
import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInteractor;
import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.ReviewedMediaRatingDataAccessInterface;
import use_case.recommendation.ScoreAdjuster;
import use_case.recommendation.WatchedMediaDataAccessInterface;
import views.HomeRecommendationsPanel;
import views.RecommendationView;
import views.SwingUiExecutor;

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
     * @param contentPreferences what kind of titles the user wants offered
     */
    public static void create(ViewManagerModel viewManagerModel,
                              RecommendationViewModel homeStripViewModel,
                              RecommendationViewModel detailedViewModel,
                              HomeRecommendationsPanel homeRecommendationsPanel,
                              RecommendationView recommendationView,
                              WatchedMediaDataAccessInterface watchedMediaDataAccess,
                              ReviewedMediaRatingDataAccessInterface reviewDataAccess,
                              AdultContentPreferenceDataAccessInterface contentPreferences) {

        // Swing lives in the view layer, so the presenter only sees an Executor.
        final Executor uiExecutor = new SwingUiExecutor();
        final TMDBAPIClient tmdbApiClient = new TMDBAPIClient();
        final MediaCatalogueDataAccessInterface catalogue = new TMDBMediaCatalogue(tmdbApiClient);
        final RecommendationDataAccessInterface userDataAccess =
                new UserActivityRecommendationDataAccess(watchedMediaDataAccess, reviewDataAccess);

        final ScoreAdjuster adjuster = chooseAdjuster();
        final int currentYear = Year.now().getValue();

        homeRecommendationsPanel.setRecommendationController(new RecommendationController(
                buildInteractor(userDataAccess, catalogue, adjuster,
                        new RecommendationPresenter(homeStripViewModel, uiExecutor), currentYear,
                        contentPreferences)));

        recommendationView.setRecommendationController(new RecommendationController(
                buildInteractor(userDataAccess, catalogue, adjuster,
                        new RecommendationPresenter(detailedViewModel, uiExecutor), currentYear,
                        contentPreferences)));

        homeRecommendationsPanel.setOpenFullListHandler(
                () -> viewManagerModel.switchView(RecommendationViewModel.VIEW_NAME));
        recommendationView.setBackHandler(
                () -> viewManagerModel.switchView(HomePageViewModel.VIEW_NAME));
    }

    private static RecommendationInputBoundary buildInteractor(
            RecommendationDataAccessInterface userDataAccess,
            MediaCatalogueDataAccessInterface catalogue,
            ScoreAdjuster adjuster,
            RecommendationOutputBoundary presenter,
            int currentYear,
            AdultContentPreferenceDataAccessInterface contentPreferences) {
        return new RecommendationInteractor(userDataAccess, catalogue, adjuster,
                presenter, currentYear, contentPreferences);
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
