package use_case.recommendation;

/**
 * How the recommendation use case reports back.
 * <p>
 * Two presenters implement this over the same interactor class: one fills the
 * home page strip, the other the detailed grouped view. Which one is in play is
 * decided when the interactor is constructed, so the use case itself never knows
 * which screen it is serving.
 */
public interface RecommendationOutputBoundary {

    /**
     * Presents the suggestions that were produced.
     *
     * @param outputData the ranked suggestions, grouped if that was requested
     */
    void presentRecommendations(RecommendationOutputData outputData);

    /**
     * Presents a reason no suggestions could be produced.
     *
     * @param errorMessage what to tell the user
     */
    void prepareFailView(String errorMessage);
}
