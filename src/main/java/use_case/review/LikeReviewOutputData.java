package use_case.review;

/**
 * Output data for liking a review.
 */
public class LikeReviewOutputData {
    private final boolean liked;

    public LikeReviewOutputData(final boolean liked) {
        this.liked = liked;
    }

    public boolean isLiked() {
        return liked;
    }
}
