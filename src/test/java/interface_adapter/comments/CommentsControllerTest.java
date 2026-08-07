package interface_adapter.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import use_case.comment.create_comment.CreateCommentInputBoundary;
import use_case.comment.create_comment.CreateCommentInputData;
import use_case.comment.delete_comment.DeleteCommentInputBoundary;
import use_case.comment.delete_comment.DeleteCommentInputData;
import use_case.comment.get_review_comments.GetReviewCommentsInputBoundary;
import use_case.comment.get_review_comments.GetReviewCommentsInputData;
import use_case.comment.like_comment.LikeCommentInputBoundary;
import use_case.comment.like_comment.LikeCommentInputData;
import use_case.comment.unlike_comment.UnlikeCommentInputBoundary;
import use_case.comment.unlike_comment.UnlikeCommentInputData;

/**
 * Tests for the comments controller.
 */
public class CommentsControllerTest {

    @Test
    void loadReviewCommentsPackagesInputData() {
        final CapturingGetReviewCommentsBoundary getBoundary =
                new CapturingGetReviewCommentsBoundary();
        final CommentsController controller = createController(getBoundary,
                new CapturingCreateCommentBoundary(),
                new CapturingDeleteCommentBoundary(),
                new CapturingLikeCommentBoundary(),
                new CapturingUnlikeCommentBoundary());

        controller.loadReviewComments("review-1");

        assertEquals("review-1", getBoundary.inputData.getReviewId());
    }

    @Test
    void createCommentPackagesInputData() {
        final CapturingCreateCommentBoundary createBoundary =
                new CapturingCreateCommentBoundary();
        final CommentsController controller = createController(
                new CapturingGetReviewCommentsBoundary(), createBoundary,
                new CapturingDeleteCommentBoundary(),
                new CapturingLikeCommentBoundary(),
                new CapturingUnlikeCommentBoundary());

        controller.createComment("review-1", "parent-1", "elodie",
                "Elodie", "I agree.");

        assertEquals("review-1", createBoundary.inputData.getReviewId());
        assertEquals("parent-1",
                createBoundary.inputData.getParentCommentId());
        assertEquals("elodie", createBoundary.inputData.getAuthorUsername());
        assertEquals("Elodie",
                createBoundary.inputData.getAuthorDisplayName());
        assertEquals("I agree.", createBoundary.inputData.getCommentText());
    }

    @Test
    void deleteLikeAndUnlikePackageInputData() {
        final CapturingDeleteCommentBoundary deleteBoundary =
                new CapturingDeleteCommentBoundary();
        final CapturingLikeCommentBoundary likeBoundary =
                new CapturingLikeCommentBoundary();
        final CapturingUnlikeCommentBoundary unlikeBoundary =
                new CapturingUnlikeCommentBoundary();
        final CommentsController controller = createController(
                new CapturingGetReviewCommentsBoundary(),
                new CapturingCreateCommentBoundary(), deleteBoundary,
                likeBoundary, unlikeBoundary);

        controller.deleteComment("comment-1", "elodie");
        controller.likeComment("comment-2", "lily");
        controller.unlikeComment("comment-3", "yidan");

        assertEquals("comment-1", deleteBoundary.inputData.getCommentId());
        assertEquals("elodie", deleteBoundary.inputData.getUsername());
        assertEquals("comment-2", likeBoundary.inputData.getCommentId());
        assertEquals("lily", likeBoundary.inputData.getUsername());
        assertEquals("comment-3", unlikeBoundary.inputData.getCommentId());
        assertEquals("yidan", unlikeBoundary.inputData.getUsername());
    }

    private CommentsController createController(
            GetReviewCommentsInputBoundary getBoundary,
            CreateCommentInputBoundary createBoundary,
            DeleteCommentInputBoundary deleteBoundary,
            LikeCommentInputBoundary likeBoundary,
            UnlikeCommentInputBoundary unlikeBoundary) {
        return new CommentsController(getBoundary, createBoundary,
                deleteBoundary, likeBoundary, unlikeBoundary);
    }

    private static final class CapturingGetReviewCommentsBoundary
            implements GetReviewCommentsInputBoundary {
        private GetReviewCommentsInputData inputData;

        @Override
        public void execute(GetReviewCommentsInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingCreateCommentBoundary
            implements CreateCommentInputBoundary {
        private CreateCommentInputData inputData;

        @Override
        public void execute(CreateCommentInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingDeleteCommentBoundary
            implements DeleteCommentInputBoundary {
        private DeleteCommentInputData inputData;

        @Override
        public void execute(DeleteCommentInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingLikeCommentBoundary
            implements LikeCommentInputBoundary {
        private LikeCommentInputData inputData;

        @Override
        public void execute(LikeCommentInputData inputData) {
            this.inputData = inputData;
        }
    }

    private static final class CapturingUnlikeCommentBoundary
            implements UnlikeCommentInputBoundary {
        private UnlikeCommentInputData inputData;

        @Override
        public void execute(UnlikeCommentInputData inputData) {
            this.inputData = inputData;
        }
    }
}
