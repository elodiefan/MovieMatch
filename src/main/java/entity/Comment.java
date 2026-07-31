package entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a comment reply of a review on a piece of media.
 */
public class Comment {
    private static final ZoneId TORONTO_ZONE = ZoneId.of("America/Toronto");

    private final String commentId;
    private final String reviewId;
    private final String parentCommentId;
    private final String authorUsername;
    private final String authorDisplayName;
    private final ZonedDateTime createdAt;
    private final Set<String> likedByUsernames;
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
        this.commentId = commentId;
        this.reviewId = reviewId;
        this.parentCommentId = parentCommentId;
        this.authorUsername = authorUsername;
        this.authorDisplayName = authorDisplayName;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.likedByUsernames = new HashSet<>(likedByUsernames);
    }

    /**
     * Returns the current date and time in Toronto.
     * @return the current Toronto date and time
     */
    public static ZonedDateTime getCurrentTorontoTime() {
        return ZonedDateTime.now(TORONTO_ZONE);
    }

    /**
     * Returns the unique identifier for this comment.
     * @return the comment id
     */
    public String getCommentId() {
        return commentId;
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
     * Returns the username of the comment author.
     * @return the author's username
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Returns the display name of the comment author.
     * @return the author's display name
     */
    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    /**
     * Returns the body of the comment.
     * @return the comment text
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Returns the time the comment was created.
     * @return the creation time
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the usernames of users who liked this comment.
     * @return a copy of the liked-by usernames
     */
    public Set<String> getLikedByUsernames() {
        return new HashSet<>(likedByUsernames);
    }

    /**
     * Returns the number of likes on this comment.
     * @return the like count
     */
    public int getLikeCount() {
        return likedByUsernames.size();
    }

    /**
     * Updates the body of the comment.
     * @param newCommentText the updated comment text
     */
    public void edit(String newCommentText) {
        this.commentText = newCommentText;
    }

    /**
     * Adds a like from a user.
     * @param username the username liking the comment
     */
    public void like(String username) {
        likedByUsernames.add(username);
    }

    /**
     * Removes a like from a user.
     * @param username the username unliking the comment
     */
    public void unlike(String username) {
        likedByUsernames.remove(username);
    }
}
