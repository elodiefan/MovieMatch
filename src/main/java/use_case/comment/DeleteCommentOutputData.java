package use_case.comment;

/**
 * Output data for deleting a comment.
 */
public class DeleteCommentOutputData {
    private final boolean deleted;

    public DeleteCommentOutputData(final boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
