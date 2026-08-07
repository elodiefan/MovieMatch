package use_case.comment.create_comment;

/** Input boundary for creating a comment. */
public interface CreateCommentInputBoundary {
    /** Executes the use case. */
    void execute(CreateCommentInputData inputData);
}
