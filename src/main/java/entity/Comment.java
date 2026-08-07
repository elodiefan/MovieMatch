package entity;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Represents a comment reply of a review on a piece of media.
 */
public class Comment extends UserContent {
    private final String reviewId;
    private final String parentCommentId;
    private String commentText;

    /**
     * Creates a comment or reply on a review.
     */
    public Comment(String commentId, String reviewId, String parentCommentId,
                   String authorUsername, String authorDisplayName, String commentText,
                   ZonedDateTime createdAt, Set<String> likedByUsernames) {
        super(commentId, authorUsername, authorDisplayName, createdAt, likedByUsernames);
        this.reviewId = reviewId;
        this.parentCommentId = parentCommentId;
        this.commentText = commentText;
    }

    /**
     * Returns the unique identifier for this comment.
     */
    public String getCommentId() {
        return getContentId();
    }

    /**
     * Returns the id of the review this comment belongs to.
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Returns the parent comment id.
     */
    public String getParentCommentId() {
        return parentCommentId;
    }

    /**
     * Returns the body of the comment.
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Updates the body of the comment.
     */
    public void edit(String newCommentText) {
        this.commentText = newCommentText;
    }

}
