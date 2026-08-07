package use_case.recommendation;

/** How the recommendation use case reports back. */
public interface RecommendationOutputBoundary {

    /** Presents the suggestions that were produced. */
    void presentRecommendations(RecommendationOutputData outputData);

    /** Presents a reason no suggestions could be produced. */
    void prepareFailView(String errorMessage);
}
