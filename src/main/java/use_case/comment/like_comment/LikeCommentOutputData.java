package use_case.comment.like_comment;

/**
 * Output data for liking a comment.
 */
public final class LikeCommentOutputData {
    /** The liked. */
    private final boolean liked;

    /**
     * Handles this review or comment operation.
     * @param inputLiked the inputLiked
     */
    public LikeCommentOutputData(final boolean inputLiked) {
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
