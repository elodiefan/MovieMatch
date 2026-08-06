package data_access;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import entity.Comment;
import use_case.comment.CommentDataAccessInterface;
import use_case.comment.create_comment.CreateCommentDataAccessInterface;
import use_case.comment.delete_comment.DeleteCommentDataAccessInterface;
import use_case.comment.get_review_comments.GetReviewCommentsDataAccessInterface;
import use_case.comment.get_user_comments.GetUserCommentsDataAccessInterface;
import use_case.comment.like_comment.LikeCommentDataAccessInterface;
import use_case.comment.unlike_comment.UnlikeCommentDataAccessInterface;

/**
 * In-memory data access object for comment data.
 */
public class InMemoryCommentDataAccessObject implements CommentDataAccessObject{
    private final Map<String, Comment> comments = new LinkedHashMap<>();

    /**
     * Saves a comment.
     * @param comment the comment to save
     */
    public void saveComment(Comment comment) {
        comments.put(comment.getCommentId(), comment);
    }

    /**
     * Returns whether a comment exists.
     * @param commentId the comment id to check
     * @return true if a comment with this id exists
     */
    public boolean existsByCommentId(String commentId) {
        return comments.containsKey(commentId);
    }

    /**
     * Returns a comment by id.
     * @param commentId the comment id to search for
     * @return the comment, if it exists
     */
    public Optional<Comment> getCommentById(String commentId) {
        return Optional.ofNullable(comments.get(commentId));
    }

    /**
     * Returns all comments on a review.
     * @param reviewId the review id
     * @return the matching comments
     */
    public List<Comment> getCommentsByReviewId(String reviewId) {
        final List<Comment> matchingComments = new ArrayList<>();

        for (Comment comment : comments.values()) {
            if (comment.getReviewId().equals(reviewId)) {
                matchingComments.add(comment);
            }
        }

        return matchingComments;
    }

    /**
     * Returns all comments written by a user.
     * @param username the author's username
     * @return the matching comments
     */
    public List<Comment> getCommentsByUsername(String username) {
        final List<Comment> matchingComments = new ArrayList<>();

        for (Comment comment : comments.values()) {
            if (comment.getAuthorUsername().equals(username)) {
                matchingComments.add(comment);
            }
        }

        return matchingComments;
    }

    /**
     * Returns all replies to a parent comment.
     * @param parentCommentId the parent comment id
     * @return the matching replies
     */
    public List<Comment> getRepliesByParentCommentId(String parentCommentId) {
        final List<Comment> matchingReplies = new ArrayList<>();

        for (Comment comment : comments.values()) {
            if (parentCommentId.equals(comment.getParentCommentId())) {
                matchingReplies.add(comment);
            }
        }

        return matchingReplies;
    }

    /**
     * Updates an existing comment.
     * @param commentId the comment id
     * @param newCommentText the updated comment text
     * @return true if the comment was updated
     */
    public boolean editComment(String commentId, String newCommentText) {
        final Optional<Comment> comment = getCommentById(commentId);
        final boolean commentExists = comment.isPresent();

        if (commentExists) {
            comment.get().edit(newCommentText);
        }

        return commentExists;
    }

    /**
     * Deletes a comment.
     * @param commentId the comment id
     * @return true if the comment was deleted
     */
    public boolean deleteComment(String commentId) {
        final boolean commentExists = existsByCommentId(commentId);

        if (commentExists) {
            comments.remove(commentId);
        }

        return commentExists;
    }

    /**
     * Adds a user's like to a comment.
     * @param commentId the comment id
     * @param username the username liking the comment
     * @return true if the comment exists
     */
    public boolean likeComment(String commentId, String username) {
        final Optional<Comment> comment = getCommentById(commentId);
        final boolean commentExists = comment.isPresent();

        if (commentExists) {
            comment.get().like(username);
        }

        return commentExists;
    }

    /**
     * Removes a user's like from a comment.
     * @param commentId the comment id
     * @param username the username unliking the comment
     * @return true if the comment exists
     */
    public boolean unlikeComment(String commentId, String username) {
        final Optional<Comment> comment = getCommentById(commentId);
        final boolean commentExists = comment.isPresent();

        if (commentExists) {
            comment.get().unlike(username);
        }

        return commentExists;
    }

    /**
     * Returns all saved comments.
     * @return all comments
     */
    public List<Comment> getAllComments() {
        return new ArrayList<>(comments.values());
    }

    @Override
    public void close() {
        // No resources to free for an in-memory store.
    }
}
