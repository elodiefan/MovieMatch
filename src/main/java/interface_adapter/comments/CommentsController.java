package interface_adapter.comments;

import use_case.comment.create_comment.CreateCommentInputBoundary;
import use_case.comment.delete_comment.DeleteCommentInputBoundary;
import use_case.comment.get_review_comments.GetReviewCommentsInputBoundary;
import use_case.comment.like_comment.LikeCommentInputBoundary;
import use_case.comment.unlike_comment.UnlikeCommentInputBoundary;

/**
 * Controller for review comments.
 */
public final class CommentsController {
    /**
     * The get review comments interactor.
     */
    private final GetReviewCommentsInputBoundary getReviewCommentsInteractor;
    /**
     * The create comment interactor.
     */
    private final CreateCommentInputBoundary createCommentInteractor;
    /**
     * The delete comment interactor.
     */
    private final DeleteCommentInputBoundary deleteCommentInteractor;
    /**
     * The like comment interactor.
     */
    private final LikeCommentInputBoundary likeCommentInteractor;
    /**
     * The unlike comment interactor.
     */
    private final UnlikeCommentInputBoundary unlikeCommentInteractor;

    /**
     * Creates a controller for comment actions.
     * @param inputGetReviewCommentsInteractor the interactor for loading
     * comments
     * @param inputCreateCommentInteractor the interactor for creating comments
     * @param inputDeleteCommentInteractor the interactor for deleting comments
     * @param inputLikeCommentInteractor the interactor for liking comments
     * @param inputUnlikeCommentInteractor the interactor for unliking comments
     */
    public CommentsController(
            final GetReviewCommentsInputBoundary
                    inputGetReviewCommentsInteractor,
            final CreateCommentInputBoundary inputCreateCommentInteractor,
            final DeleteCommentInputBoundary inputDeleteCommentInteractor,
            final LikeCommentInputBoundary inputLikeCommentInteractor,
            final UnlikeCommentInputBoundary inputUnlikeCommentInteractor) {
        this.getReviewCommentsInteractor = inputGetReviewCommentsInteractor;
        this.createCommentInteractor = inputCreateCommentInteractor;
        this.deleteCommentInteractor = inputDeleteCommentInteractor;
        this.likeCommentInteractor = inputLikeCommentInteractor;
        this.unlikeCommentInteractor = inputUnlikeCommentInteractor;
    }

    /**
     * Loads persisted comments for one review.
     * @param reviewId the review id to load comments for
     */
    public void loadReviewComments(final String reviewId) {
        getReviewCommentsInteractor.execute(reviewId);
    }

    /**
     * Creates a comment or reply on a review.
     * @param reviewId the id of the review being commented on
     * @param parentCommentId the parent comment id, or null for top level
     * @param authorUsername the comment author's username
     * @param authorDisplayName the comment author's display name
     * @param commentText the comment text
     */
    public void createComment(final String reviewId,
                              final String parentCommentId,
                              final String authorUsername,
                              final String authorDisplayName,
                              final String commentText) {
        createCommentInteractor.execute(reviewId, parentCommentId,
                authorUsername, authorDisplayName, commentText);
    }

    /**
     * Deletes one persisted comment written by the given user.
     * @param commentId the id of the comment to delete
     * @param username the username of the user deleting the comment
     */
    public void deleteComment(final String commentId, final String username) {
        deleteCommentInteractor.execute(commentId, username);
    }

    /**
     * Likes one persisted comment.
     * @param commentId the id of the comment to like
     * @param username the username of the user liking the comment
     */
    public void likeComment(final String commentId, final String username) {
        likeCommentInteractor.execute(commentId, username);
    }

    /**
     * Unlikes one persisted comment.
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     */
    public void unlikeComment(final String commentId, final String username) {
        unlikeCommentInteractor.execute(commentId, username);
    }
}
