package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Comment;
import use_case.comment.delete_comment.DeleteCommentDataAccessInterface;
import use_case.comment.delete_comment.DeleteCommentInteractor;
import use_case.comment.delete_comment.DeleteCommentOutputBoundary;

class DeleteCommentInteractorTest {

    @Test
    void authorCanDeleteComment() {
        final Comment comment = new Comment("comment-1", "review-1", null,
                "bob", "Bob", "Text", ZonedDateTime.now(), Set.of());
        final RecordingDataAccess dataAccess = new RecordingDataAccess(comment, true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new DeleteCommentInteractor(dataAccess, presenter).execute(" comment-1 ", " bob ");

        assertEquals("comment-1", dataAccess.deletedId);
        assertTrue(presenter.success);
    }

    @Test
    void differentUserCannotDeleteComment() {
        final Comment comment = new Comment("comment-1", "review-1", null,
                "alice", "Alice", "Text", ZonedDateTime.now(), Set.of());
        final RecordingDataAccess dataAccess = new RecordingDataAccess(comment, true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new DeleteCommentInteractor(dataAccess, presenter).execute("comment-1", "bob");

        assertEquals("Comment could not be deleted.", presenter.failure);
        assertFalse(dataAccess.deleteCalled);
    }

    @Test
    void missingCommentCannotBeDeleted() {
        final RecordingDataAccess dataAccess = new RecordingDataAccess(null, true);
        final RecordingPresenter presenter = new RecordingPresenter();

        new DeleteCommentInteractor(dataAccess, presenter).execute("comment-1", "bob");

        assertEquals("Comment could not be deleted.", presenter.failure);
        assertFalse(dataAccess.deleteCalled);
    }

    @Test
    void failedDaoDeletionIsReportedAsFailure() {
        final Comment comment = new Comment("comment-1", "review-1", null,
                "bob", "Bob", "Text", ZonedDateTime.now(), Set.of());
        final RecordingPresenter presenter = new RecordingPresenter();

        new DeleteCommentInteractor(new RecordingDataAccess(comment, false), presenter)
                .execute("comment-1", "bob");

        assertEquals("Comment could not be deleted.", presenter.failure);
    }

    @Test
    void blankCommentIdIsReportedAsFailure() {
        assertFailure(" ", "bob", "Comment id cannot be empty.");
    }

    @Test
    void blankUsernameIsReportedAsFailure() {
        assertFailure("comment-1", " ", "Username cannot be empty.");
    }

    @Test
    void nullCommentIdIsReportedAsFailure() {
        assertFailure(null, "bob", "Comment id cannot be empty.");
    }

    @Test
    void nullUsernameIsReportedAsFailure() {
        assertFailure("comment-1", null, "Username cannot be empty.");
    }

    @Test
    void missingDataAccessIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new DeleteCommentInteractor(null, presenter).execute("comment-1", "bob");
        assertEquals("Comment data access object has not been configured.",
                presenter.failure);
    }

    private static void assertFailure(final String commentId, final String username,
                                      final String expectedMessage) {
        final RecordingPresenter presenter = new RecordingPresenter();
        new DeleteCommentInteractor(new RecordingDataAccess(null, true), presenter)
                .execute(commentId, username);
        assertEquals(expectedMessage, presenter.failure);
    }

    private static final class RecordingDataAccess implements DeleteCommentDataAccessInterface {
        private final Comment comment;
        private final boolean deleteResult;
        private boolean deleteCalled;
        private String deletedId;

        private RecordingDataAccess(Comment comment, boolean deleteResult) {
            this.comment = comment;
            this.deleteResult = deleteResult;
        }

        @Override
        public Optional<Comment> getCommentById(String commentId) {
            return Optional.ofNullable(comment);
        }

        @Override
        public boolean deleteComment(String commentId) {
            deleteCalled = true;
            deletedId = commentId;
            return deleteResult;
        }
    }

    private static final class RecordingPresenter implements DeleteCommentOutputBoundary {
        private boolean success;
        private String failure;

        @Override
        public void prepareSuccessView(boolean deleted) {
            success = deleted;
        }

        @Override
        public String prepareFailView(String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
