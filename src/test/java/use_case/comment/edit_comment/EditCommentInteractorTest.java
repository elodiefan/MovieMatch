package use_case.comment.edit_comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import entity.Comment;

/** Tests for {@link EditCommentInteractor}. */
class EditCommentInteractorTest {

    @Test
    void authorCanEditOwnComment() {
        final RecordingDao dao = new RecordingDao(commentByBob());
        final RecordingPresenter presenter = new RecordingPresenter();

        new EditCommentInteractor(dao, presenter).execute(
                " comment-1 ", " bob ", " Updated comment ");

        assertTrue(dao.edited);
        assertEquals("comment-1", dao.commentId);
        assertEquals("Updated comment", dao.commentText);
        assertTrue(presenter.success);
        assertNull(presenter.failure);
    }

    @Test
    void anotherUserCannotEditComment() {
        final RecordingDao dao = new RecordingDao(commentByBob());
        final RecordingPresenter presenter = new RecordingPresenter();

        new EditCommentInteractor(dao, presenter).execute(
                "comment-1", "alice", "Changed");

        assertFalse(dao.edited);
        assertFalse(presenter.success);
        assertEquals("Comment could not be edited.", presenter.failure);
    }

    @Test
    void missingCommentIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new EditCommentInteractor(new RecordingDao(null), presenter).execute(
                "comment-1", "bob", "Changed");

        assertEquals("Comment could not be edited.", presenter.failure);
    }

    @Test
    void blankCommentTextIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new EditCommentInteractor(new RecordingDao(commentByBob()), presenter).execute(
                "comment-1", "bob", "   ");

        assertEquals("Comment text cannot be empty.", presenter.failure);
    }

    private static Comment commentByBob() {
        return new Comment("comment-1", "review-1", null, "bob", "Bob",
                "Original", ZonedDateTime.parse("2026-01-01T12:00:00-05:00"),
                new HashSet<>());
    }

    private static final class RecordingDao implements EditCommentDataAccessInterface {
        private final Comment storedComment;
        private boolean edited;
        private String commentId;
        private String commentText;

        private RecordingDao(final Comment comment) {
            storedComment = comment;
        }

        @Override
        public Optional<Comment> getCommentById(final String id) {
            return Optional.ofNullable(storedComment);
        }

        @Override
        public boolean editComment(final String id, final String newText) {
            edited = true;
            commentId = id;
            commentText = newText;
            return true;
        }
    }

    private static final class RecordingPresenter implements EditCommentOutputBoundary {
        private boolean success;
        private String failure;

        @Override
        public void prepareSuccessView(final boolean edited) {
            success = edited;
        }

        @Override
        public String prepareFailView(final String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }
}
