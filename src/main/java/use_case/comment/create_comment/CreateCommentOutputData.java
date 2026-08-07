package use_case.comment.create_comment;

import entity.Comment;
import use_case.comment.CommentSummaryData;
import use_case.comment.CommentSummaryMapper;

/**
 * Output data for creating a comment.
 */
public final class CreateCommentOutputData {
    /** The comment. */
    private final CommentSummaryData comment;

    /**
     * Handles this review or comment operation.
     */
    public CreateCommentOutputData(final Comment inputComment) {
        this.comment = CommentSummaryMapper.toSummary(inputComment);
    }

    /**
     * Handles this review or comment operation.
     */
    public CommentSummaryData getComment() {
        return comment;
    }
}
