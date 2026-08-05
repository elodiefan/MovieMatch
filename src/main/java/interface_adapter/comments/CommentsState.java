package interface_adapter.comments;

import java.util.ArrayList;
import java.util.List;

/**
 * State for review comments.
 */
public class CommentsState {
    private String reviewId = "";
    private List<CommentsPresenter.CommentRow> comments = new ArrayList<>();
    private String selectedCommentId = "";
    private String parentCommentId = "";
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
     * @param reviewId the review id
     */
    public void setReviewId(final String reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Returns the displayed comment rows.
     * @return a copy of the comment rows
     */
    public List<CommentsPresenter.CommentRow> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * Sets the displayed comment rows.
     * @param comments the comment rows
     */
    public void setComments(
            final List<CommentsPresenter.CommentRow> comments) {
        this.comments = new ArrayList<>(comments);
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
     * @param selectedCommentId the selected comment id
     */
    public void setSelectedCommentId(final String selectedCommentId) {
        this.selectedCommentId = selectedCommentId;
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
     * @param parentCommentId the parent comment id
     */
    public void setParentCommentId(final String parentCommentId) {
        this.parentCommentId = parentCommentId;
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
     * @param commentsError the error message
     */
    public void setCommentsError(final String commentsError) {
        this.commentsError = commentsError;
    }
}
