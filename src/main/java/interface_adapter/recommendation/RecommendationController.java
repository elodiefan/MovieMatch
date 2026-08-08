package interface_adapter.recommendation;

import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInputData;

/**
 * Turns a click into a request the recommendation use case understands.
 */
public class RecommendationController {

    private final RecommendationInputBoundary recommendationInteractor;

    public RecommendationController(
            RecommendationInputBoundary recommendationInteractor) {
        this.recommendationInteractor = recommendationInteractor;
    }

    /**
     * Loads the short, ungrouped list the home page shows.
     *
     * @param username the username
     */
    public void loadForHomePage(String username) {
        recommendationInteractor.recommend(new RecommendationInputData(
                username, RecommendationInputData.HOME_PAGE_LIMIT, false));
    }

    /**
     * Loads the longer list, grouped into genre sections, for the full screen.
     *
     * @param username the username
     */
    public void loadDetailed(String username) {
        recommendationInteractor.recommend(new RecommendationInputData(
                username, RecommendationInputData.DETAILED_LIMIT, true));
    }
}
