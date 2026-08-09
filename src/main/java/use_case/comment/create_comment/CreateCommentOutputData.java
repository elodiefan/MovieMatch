package use_case.comment.create_comment;

/**
 * Output data for creating a comment.
 */
public final class CreateCommentOutputData {
    /** The comment. */
    private final boolean created;

    /**
     * Creates output data for the create comment result.
     * @param inputCreated whether the comment was created
     */
    public CreateCommentOutputData(final boolean inputCreated) {
        this.created = inputCreated;
    }

    /**
     * Returns whether the comment was created.
     * @return true when the comment was created
     */
    public boolean isCreated() {
        return created;
    }
}
