package use_case.review.unlike_review;

/**
 * Output data for unliking a review.
 */
public final class UnlikeReviewOutputData {
    /** The unliked. */
    private final boolean unliked;

    /**
     * Handles this review or comment operation.
     * @param inputUnliked the inputUnliked
     */
    public UnlikeReviewOutputData(final boolean inputUnliked) {
        this.unliked = inputUnliked;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public boolean isUnliked() {
        return unliked;
    }
}
