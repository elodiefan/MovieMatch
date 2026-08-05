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
     * @param commentId the unique identifier for this comment
     * @param reviewId the id of the review this comment belongs to
     * @param parentCommentId the parent comment id, or null if this is a top-level comment
     * @param authorUsername the username of the comment author
     * @param authorDisplayName the display name of the comment author
     * @param commentText the body of the comment
     * @param createdAt the time the comment was created
     * @param likedByUsernames the usernames of users who liked this comment
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
     * @return the comment id
     */
    public String getCommentId() {
        return getContentId();
    }

    /**
     * Returns the id of the review this comment belongs to.
     * @return the review id
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * Returns the parent comment id.
     * @return the parent comment id, or null if this is a top-level comment
     */
    public String getParentCommentId() {
        return parentCommentId;
    }

    /**
     * Returns the body of the comment.
     * @return the comment text
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Updates the body of the comment.
     * @param newCommentText the updated comment text
     */
    public void edit(String newCommentText) {
        this.commentText = newCommentText;
    }

}
