package interface_adapter.comments;

import use_case.create_comment.CreateCommentInputBoundary;
import use_case.delete_comment.DeleteCommentInputBoundary;
import use_case.get_review_comments.GetReviewCommentsInputBoundary;
import use_case.like_comment.LikeCommentInputBoundary;
import use_case.unlike_comment.UnlikeCommentInputBoundary;

/**
 * Controller for review comments.
 */
public final class CommentsController {
    /** The get review comments interactor. */
    private final GetReviewCommentsInputBoundary getReviewCommentsInteractor;
    /** The create comment interactor. */
    private final CreateCommentInputBoundary createCommentInteractor;
    /** The delete comment interactor. */
    private final DeleteCommentInputBoundary deleteCommentInteractor;
    /** The like comment interactor. */
    private final LikeCommentInputBoundary likeCommentInteractor;
    /** The unlike comment interactor. */
    private final UnlikeCommentInputBoundary unlikeCommentInteractor;

    /**
     * Creates a controller for comment actions.
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
     */
    public void loadReviewComments(final String reviewId) {
        getReviewCommentsInteractor.execute(reviewId);
    }

    /**
     * Creates a comment or reply on a review.
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
     */
    public void deleteComment(final String commentId, final String username) {
        deleteCommentInteractor.execute(commentId, username);
    }

    /**
     * Likes one persisted comment.
     */
    public void likeComment(final String commentId, final String username) {
        likeCommentInteractor.execute(commentId, username);
    }

    /**
     * Unlikes one persisted comment.
     */
    public void unlikeComment(final String commentId, final String username) {
        unlikeCommentInteractor.execute(commentId, username);
    }
}
