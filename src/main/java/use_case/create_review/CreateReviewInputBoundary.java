package use_case.create_review;

/**
 * Input boundary for creating a review.
 */
public interface CreateReviewInputBoundary {
    /**
     * Executes the use case.
     */
    void execute(int mediaId, String mediaType, String mediaTitle,
                 String authorUsername, String authorDisplayName,
                 double rating, String reviewText);
}
