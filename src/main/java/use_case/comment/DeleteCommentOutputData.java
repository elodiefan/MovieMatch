package use_case.comment;

/**
 * Output data for deleting a comment.
 */
public final class DeleteCommentOutputData {
    /** The deleted. */
    private final boolean deleted;

    /**
     * Handles this review or comment operation.
     * @param inputDeleted the inputDeleted
     */
    public DeleteCommentOutputData(final boolean inputDeleted) {
        this.deleted = inputDeleted;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public boolean isDeleted() {
        return deleted;
    }
}
