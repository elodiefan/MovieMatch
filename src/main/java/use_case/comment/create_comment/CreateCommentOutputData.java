package use_case.comment.create_comment;

import entity.Comment;

/**
 * Output data for creating a comment.
 */
public final class CreateCommentOutputData {
    /** The comment. */
    private final Comment comment;

    /**
     * Handles this review or comment operation.
     * @param inputComment the inputComment
     */
    public CreateCommentOutputData(final Comment inputComment) {
        this.comment = inputComment;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public Comment getComment() {
        return comment;
    }
}
