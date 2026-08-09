package interface_adapter.comments;

import java.util.ArrayList;
import java.util.List;

/**
 * State for review comments.
 */
public final class CommentsState {
    /**
     * The review id.
     */
    private String reviewId = "";
    /**
     * The comments.
     */
    private List<CommentRow> comments = new ArrayList<>();
    /**
     * The selected comment id.
     */
    private String selectedCommentId = "";
    /**
     * The parent comment id.
     */
    private String parentCommentId = "";
    /**
     * The comments error.
     */
    private String commentsError;

    /**
     * Returns the review id whose comments are displayed.
     * @return the review id
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Sets the review id whose comments are displayed.
     * @param inputReviewId the review id
     */
    public void setReviewId(final String inputReviewId) {
        this.reviewId = inputReviewId;
    }

    /**
     * Returns the displayed comment rows.
     * @return a copy of the comment rows
     */
    public List<CommentRow> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * Sets the displayed comment rows.
     * @param inputComments the comment rows
     */
    public void setComments(final List<CommentRow> inputComments) {
        this.comments = new ArrayList<>(inputComments);
    }

    /**
     * Returns the selected comment id.
     * @return the selected comment id
     */
    public String getSelectedCommentId() {
        return selectedCommentId;
    }

    /**
     * Sets the selected comment id.
     * @param inputSelectedCommentId the selected comment id
     */
    public void setSelectedCommentId(final String inputSelectedCommentId) {
        this.selectedCommentId = inputSelectedCommentId;
    }

    /**
     * Returns the parent comment id for a reply being written.
     * @return the parent comment id
     */
    public String getParentCommentId() {
        return parentCommentId;
    }

    /**
     * Sets the parent comment id for a reply being written.
     * @param inputParentCommentId the parent comment id
     */
    public void setParentCommentId(final String inputParentCommentId) {
        this.parentCommentId = inputParentCommentId;
    }

    /**
     * Returns the current comments error message.
     * @return the error message
     */
    public String getCommentsError() {
        return commentsError;
    }

    /**
     * Sets the current comments error message.
     * @param inputCommentsError the error message
     */
    public void setCommentsError(final String inputCommentsError) {
        this.commentsError = inputCommentsError;
    }
}
