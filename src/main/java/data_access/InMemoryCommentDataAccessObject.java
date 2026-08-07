package data_access;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import entity.Comment;
import use_case.comment.CommentDataAccessInterface;

/**
 * In-memory data access object for comment data.
 */
public class InMemoryCommentDataAccessObject implements CommentDataAccessInterface {
    private final Map<String, Comment> comments = new LinkedHashMap<>();

    /**
     * Saves a comment.
     */
    public void saveComment(Comment comment) {
        comments.put(comment.getCommentId(), comment);
    }

    /**
     * Returns whether a comment exists.
     */
    public boolean existsByCommentId(String commentId) {
        return comments.containsKey(commentId);
    }

    /**
     * Returns a comment by id.
     */
    public Optional<Comment> getCommentById(String commentId) {
        return Optional.ofNullable(comments.get(commentId));
    }

    /**
     * Returns all comments on a review.
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
     */
    public List<Comment> getAllComments() {
        return new ArrayList<>(comments.values());
    }

    public void close() {
        // No resources to free for an in-memory store.
    }
}
