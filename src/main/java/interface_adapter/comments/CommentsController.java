package interface_adapter.comments;

import java.util.List;

import entity.Comment;
import use_case.comment.CreateCommentInteractor;
import use_case.comment.DeleteCommentInteractor;
import use_case.comment.GetReviewCommentsInteractor;
import use_case.comment.LikeCommentInteractor;
import use_case.comment.UnlikeCommentInteractor;

/**
 * Controller for review comments.
 */
public class CommentsController {
    private final GetReviewCommentsInteractor getReviewCommentsInteractor;
    private final CreateCommentInteractor createCommentInteractor;
    private final DeleteCommentInteractor deleteCommentInteractor;
    private final LikeCommentInteractor likeCommentInteractor;
    private final UnlikeCommentInteractor unlikeCommentInteractor;

    /**
     * Creates a controller for comment actions.
     * @param getReviewCommentsInteractor the interactor for loading comments
     * @param createCommentInteractor the interactor for creating comments
     * @param deleteCommentInteractor the interactor for deleting comments
     * @param likeCommentInteractor the interactor for liking comments
     * @param unlikeCommentInteractor the interactor for unliking comments
     */
    public CommentsController(
            final GetReviewCommentsInteractor getReviewCommentsInteractor,
            final CreateCommentInteractor createCommentInteractor,
            final DeleteCommentInteractor deleteCommentInteractor,
            final LikeCommentInteractor likeCommentInteractor,
            final UnlikeCommentInteractor unlikeCommentInteractor) {
        this.getReviewCommentsInteractor = getReviewCommentsInteractor;
        this.createCommentInteractor = createCommentInteractor;
        this.deleteCommentInteractor = deleteCommentInteractor;
        this.likeCommentInteractor = likeCommentInteractor;
        this.unlikeCommentInteractor = unlikeCommentInteractor;
    }

    /**
     * Loads comments for one review.
     * @param reviewId the review id to load comments for
     * @param comments the comments to search through
     * @return the matching comments
     */
    public List<Comment> getReviewComments(final String reviewId,
                                           final List<Comment> comments) {
        return getReviewCommentsInteractor.getReviewComments(reviewId,
                comments);
    }

    /**
     * Creates a comment or reply on a review.
     * @param reviewId the id of the review being commented on
     * @param parentCommentId the parent comment id, or null for top level
     * @param authorUsername the comment author's username
     * @param authorDisplayName the comment author's display name
     * @param commentText the comment text
     * @return the created comment
     */
    public Comment createComment(final String reviewId,
                                 final String parentCommentId,
                                 final String authorUsername,
                                 final String authorDisplayName,
                                 final String commentText) {
        return createCommentInteractor.createComment(reviewId,
                parentCommentId, authorUsername, authorDisplayName,
                commentText);
    }

    /**
     * Deletes one comment written by the given user.
     * @param commentId the id of the comment to delete
     * @param username the username of the user deleting the comment
     * @param comments the comments to search through
     * @return true if the comment was deleted
     */
    public boolean deleteComment(final String commentId,
                                 final String username,
                                 final List<Comment> comments) {
        return deleteCommentInteractor.deleteComment(commentId, username,
                comments);
    }

    /**
     * Likes a comment.
     * @param commentId the id of the comment to like
     * @param username the username of the user liking the comment
     * @param comments the comments to search through
     * @return true if the comment was found and liked
     */
    public boolean likeComment(final String commentId, final String username,
                               final List<Comment> comments) {
        return likeCommentInteractor.likeComment(commentId, username,
                comments);
    }

    /**
     * Unlikes a comment.
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     * @param comments the comments to search through
     * @return true if the comment was found and unliked
     */
    public boolean unlikeComment(final String commentId,
                                 final String username,
                                 final List<Comment> comments) {
        return unlikeCommentInteractor.unlikeComment(commentId, username,
                comments);
    }
}
