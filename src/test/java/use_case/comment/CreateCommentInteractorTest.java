package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import entity.Comment;
import use_case.comment.create_comment.CreateCommentDataAccessInterface;
import use_case.comment.create_comment.CreateCommentInputData;
import use_case.comment.create_comment.CreateCommentInteractor;
import use_case.comment.create_comment.CreateCommentOutputBoundary;

/**
 * Tests for the create comment interactor.
 */
class CreateCommentInteractorTest {

    @Test
    void successSavesTheNewComment() {
        final RecordingCommentDao dao = new RecordingCommentDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateCommentInteractor interactor = new CreateCommentInteractor(dao, presenter);

        interactor.execute(new CreateCommentInputData(" review-1 ", null, " bob ", " Bob ", " Nice review! "));

        assertNotNull(dao.savedComment);
        assertEquals("review-1", dao.savedComment.getReviewId());
        assertEquals("bob", dao.savedComment.getAuthorUsername());
        assertEquals("Nice review!", dao.savedComment.getCommentText());
        assertTrue(presenter.created);
        assertNull(presenter.failure);
    }

    @Test
    void blankCommentIsReportedAsFailure() {
        final RecordingCommentDao dao = new RecordingCommentDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateCommentInteractor interactor = new CreateCommentInteractor(dao, presenter);

        interactor.execute(new CreateCommentInputData("review-1", null, "bob", "Bob", "   "));

        assertNull(dao.savedComment);
        assertEquals("Comment text cannot be empty.", presenter.failure);
    }

    @Test
    void blankReviewIdIsReportedAsFailure() {
        assertFailure(" ", "bob", "Bob", "Text",
                "Review id cannot be empty.");
    }

    @Test
    void blankUsernameIsReportedAsFailure() {
        assertFailure("review-1", " ", "Bob", "Text",
                "Author username cannot be empty.");
    }

    @Test
    void blankDisplayNameIsReportedAsFailure() {
        assertFailure("review-1", "bob", " ", "Text",
                "Author display name cannot be empty.");
    }

    @Test
    void nullReviewIdIsReportedAsFailure() {
        assertFailure(null, "bob", "Bob", "Text",
                "Review id cannot be empty.");
    }

    @Test
    void missingDataAccessIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new CreateCommentInteractor(null, presenter).execute(
                new CreateCommentInputData("review-1", null, "bob", "Bob", "Text"));

        assertEquals("Comment data access object has not been configured.",
                presenter.failure);
    }

    @Test
    void replyKeepsTrimmedParentCommentId() {
        final RecordingCommentDao dao = new RecordingCommentDao();
        final RecordingPresenter presenter = new RecordingPresenter();

        new CreateCommentInteractor(dao, presenter).execute(
                new CreateCommentInputData("review-1", " parent-1 ", "bob", "Bob", "Reply"));

        assertEquals("parent-1", dao.savedComment.getParentCommentId());
    }

    private static void assertFailure(final String reviewId, final String username,
                                      final String displayName, final String text,
                                      final String expectedMessage) {
        final RecordingPresenter presenter = new RecordingPresenter();

        new CreateCommentInteractor(new RecordingCommentDao(), presenter).execute(
                new CreateCommentInputData(reviewId, null, username, displayName, text));

        assertEquals(expectedMessage, presenter.failure);
    }

    private static final class RecordingCommentDao implements CreateCommentDataAccessInterface {
        private Comment savedComment;

        @Override
        public void saveComment(final Comment comment) {
            savedComment = comment;
        }
    }

    private static final class RecordingPresenter implements CreateCommentOutputBoundary {
        private boolean created;
        private String failure;

        @Override
        public void prepareSuccessView(final boolean commentCreated) {
            created = commentCreated;
        }

        @Override
        public String prepareFailView(final String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
