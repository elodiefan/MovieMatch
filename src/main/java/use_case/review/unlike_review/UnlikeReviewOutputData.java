package use_case.review.unlike_review;

/** Output data for unliking a review. */
public final class UnlikeReviewOutputData {
    /** The unliked. */
    private final boolean unliked;

    /** Handles this review or comment operation. */
    public UnlikeReviewOutputData(final boolean inputUnliked) {
        this.unliked = inputUnliked;
    }

    /** Handles this review or comment operation. */
    public boolean isUnliked() {
        return unliked;
    }
}
