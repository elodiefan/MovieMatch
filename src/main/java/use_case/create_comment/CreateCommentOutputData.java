package use_case.create_comment;

/**
 * Output data for creating a comment.
 */
public final class CreateCommentOutputData {
    /** The comment. */
    private final boolean created;

    /**
     * Handles this review or comment operation.
     */
    public CreateCommentOutputData(final boolean inputCreated) {
        this.created = inputCreated;
    }

    /**
     * Handles this review or comment operation.
     */
    public boolean isCreated() {
        return created;
    }
}
