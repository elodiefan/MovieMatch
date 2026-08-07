package use_case.delete_comment;

/**
 * Output data for deleting a comment.
 */
public final class DeleteCommentOutputData {
    /** The deleted. */
    private final boolean deleted;

    /**
     * Handles this review or comment operation.
     */
    public DeleteCommentOutputData(final boolean inputDeleted) {
        this.deleted = inputDeleted;
    }

    /**
     * Handles this review or comment operation.
     */
    public boolean isDeleted() {
        return deleted;
    }
}
