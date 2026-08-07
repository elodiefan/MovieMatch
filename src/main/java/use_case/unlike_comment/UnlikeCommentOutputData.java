package use_case.unlike_comment;

/**
 * Output data for unliking a comment.
 */
public final class UnlikeCommentOutputData {
    /** The unliked. */
    private final boolean unliked;

    /**
     * Handles this review or comment operation.
     */
    public UnlikeCommentOutputData(final boolean inputUnliked) {
        this.unliked = inputUnliked;
    }

    /**
     * Handles this review or comment operation.
     */
    public boolean isUnliked() {
        return unliked;
    }
}
