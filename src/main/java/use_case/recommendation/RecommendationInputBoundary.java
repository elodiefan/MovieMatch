package use_case.recommendation;

/** What the interface adapter layer may ask the recommendation use case to do. */
public interface RecommendationInputBoundary {

    /** Produces recommendations for a user and hands them to the presenter. */
    void recommend(RecommendationInputData inputData);
}
