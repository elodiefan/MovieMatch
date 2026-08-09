package use_case.review.like_review;

/**
 * Output data for liking a review.
 */
public final class LikeReviewOutputData {
    /**
     * The liked.
     */
    private final boolean liked;

    /**
     * Handles this review or comment operation.
     * @param inputLiked the inputLiked
     */
    public LikeReviewOutputData(final boolean inputLiked) {
        this.liked = inputLiked;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public boolean isLiked() {
        return liked;
    }
}
