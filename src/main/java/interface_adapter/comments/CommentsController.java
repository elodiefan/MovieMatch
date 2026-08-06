package interface_adapter.comments;

import use_case.comment.CreateCommentInputBoundary;
import use_case.comment.CreateCommentInputData;
import use_case.comment.DeleteCommentInputBoundary;
import use_case.comment.DeleteCommentInputData;
import use_case.comment.GetReviewCommentsInputBoundary;
import use_case.comment.GetReviewCommentsInputData;
import use_case.comment.LikeCommentInputBoundary;
import use_case.comment.LikeCommentInputData;
import use_case.comment.UnlikeCommentInputBoundary;
import use_case.comment.UnlikeCommentInputData;

/**
 * Controller for review comments.
 */
public class CommentsController {
    private final GetReviewCommentsInputBoundary getReviewCommentsInteractor;
    private final CreateCommentInputBoundary createCommentInteractor;
    private final DeleteCommentInputBoundary deleteCommentInteractor;
    private final LikeCommentInputBoundary likeCommentInteractor;
    private final UnlikeCommentInputBoundary unlikeCommentInteractor;

    /**
     * Creates a controller for comment actions.
     * @param getReviewCommentsInteractor the interactor for loading comments
     * @param createCommentInteractor the interactor for creating comments
     * @param deleteCommentInteractor the interactor for deleting comments
     * @param likeCommentInteractor the interactor for liking comments
     * @param unlikeCommentInteractor the interactor for unliking comments
     */
    public CommentsController(
            final GetReviewCommentsInputBoundary getReviewCommentsInteractor,
            final CreateCommentInputBoundary createCommentInteractor,
            final DeleteCommentInputBoundary deleteCommentInteractor,
            final LikeCommentInputBoundary likeCommentInteractor,
            final UnlikeCommentInputBoundary unlikeCommentInteractor) {
        this.getReviewCommentsInteractor = getReviewCommentsInteractor;
        this.createCommentInteractor = createCommentInteractor;
        this.deleteCommentInteractor = deleteCommentInteractor;
        this.likeCommentInteractor = likeCommentInteractor;
        this.unlikeCommentInteractor = unlikeCommentInteractor;
    }

    /**
     * Loads persisted comments for one review.
     * @param reviewId the review id to load comments for
     */
    public void loadReviewComments(final String reviewId) {
        getReviewCommentsInteractor.execute(new GetReviewCommentsInputData(
                reviewId));
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
        createCommentInteractor.execute(new CreateCommentInputData(reviewId,
                parentCommentId, authorUsername, authorDisplayName,
                commentText));
    }

    /**
     * Deletes one persisted comment written by the given user.
     * @param commentId the id of the comment to delete
     * @param username the username of the user deleting the comment
     */
    public void deleteComment(final String commentId, final String username) {
        deleteCommentInteractor.execute(new DeleteCommentInputData(commentId,
                username));
    }

    /**
     * Likes one persisted comment.
     * @param commentId the id of the comment to like
     * @param username the username of the user liking the comment
     */
    public void likeComment(final String commentId, final String username) {
        likeCommentInteractor.execute(new LikeCommentInputData(commentId,
                username));
    }

    /**
     * Unlikes one persisted comment.
     * @param commentId the id of the comment to unlike
     * @param username the username of the user unliking the comment
     */
    public void unlikeComment(final String commentId, final String username) {
        unlikeCommentInteractor.execute(new UnlikeCommentInputData(commentId,
                username));
    }
}
