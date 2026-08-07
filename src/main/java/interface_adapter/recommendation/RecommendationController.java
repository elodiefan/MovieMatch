package interface_adapter.recommendation;

import interface_adapter.ViewManagerModel;
import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInputData;

/** Turns a click into a request the recommendation use case understands. */
public class RecommendationController {

    private final RecommendationInputBoundary recommendationInteractor;
    private final ViewManagerModel viewManagerModel;
    private final String homePageViewName;

    public RecommendationController(RecommendationInputBoundary recommendationInteractor,
                                    ViewManagerModel viewManagerModel,
                                    String homePageViewName) {
        this.recommendationInteractor = recommendationInteractor;
        this.viewManagerModel = viewManagerModel;
        this.homePageViewName = homePageViewName;
    }

    /** Loads the short, ungrouped list the home page shows. */
    public void loadForHomePage(String username) {
        recommendationInteractor.recommend(new RecommendationInputData(
                username, RecommendationInputData.HOME_PAGE_LIMIT, false));
    }

    /** Loads the longer list, grouped into genre sections, for the full screen. */
    public void loadDetailed(String username) {
        recommendationInteractor.recommend(new RecommendationInputData(
                username, RecommendationInputData.DETAILED_LIMIT, true));
    }

    /** Opens the full recommendation screen. */
    public void switchToRecommendationView() {
        viewManagerModel.switchView(RecommendationViewModel.VIEW_NAME);
    }

    /** Returns to the home page. */
    public void switchToHomePageView() {
        viewManagerModel.switchView(homePageViewName);
    }
}
