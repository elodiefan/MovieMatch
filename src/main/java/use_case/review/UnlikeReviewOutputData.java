package use_case.review;

/**
 * Output data for unliking a review.
 */
public class UnlikeReviewOutputData {
    private final boolean unliked;

    public UnlikeReviewOutputData(final boolean unliked) {
        this.unliked = unliked;
    }

    public boolean isUnliked() {
        return unliked;
    }
}
