package use_case.like_review;

/**
 * Output data for liking a review.
 */
public final class LikeReviewOutputData {
    /** The liked. */
    private final boolean liked;

    /**
     * Handles this review or comment operation.
     */
    public LikeReviewOutputData(final boolean inputLiked) {
        this.liked = inputLiked;
    }

    /**
     * Handles this review or comment operation.
     */
    public boolean isLiked() {
        return liked;
    }
}
