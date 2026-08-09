package use_case.recommendation;

/**
 * How the recommendation use case reports back.
 */
public interface RecommendationOutputBoundary {

    /**
     * Presents the suggestions that were produced.
     *
     * @param outputData the output data
     */
    void presentRecommendations(RecommendationOutputData outputData);

    /**
     * Presents a reason no suggestions could be produced.
     *
     * @param errorMessage the error message
     */
    void prepareFailView(String errorMessage);
}
