package use_case.comment.unlike_comment;

/**
 * Output data for unliking a comment.
 */
public final class UnlikeCommentOutputData {
    /**
     * The unliked.
     */
    private final boolean unliked;

    /**
     * Handles this review or comment operation.
     * @param inputUnliked the inputUnliked
     */
    public UnlikeCommentOutputData(final boolean inputUnliked) {
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
