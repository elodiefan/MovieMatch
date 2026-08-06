package use_case.comment;

import entity.Comment;

/**
 * Output data for creating a comment.
 */
public class CreateCommentOutputData {
    private final Comment comment;

    public CreateCommentOutputData(final Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }
}
