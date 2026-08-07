package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

import data_access.InMemoryCommentDataAccessObject;
import entity.Comment;
import org.junit.jupiter.api.Test;
import use_case.comment.delete_comment.DeleteCommentInputBoundary;
import use_case.comment.delete_comment.DeleteCommentInputData;
import use_case.comment.delete_comment.DeleteCommentInteractor;
import use_case.comment.delete_comment.DeleteCommentOutputBoundary;
import use_case.comment.delete_comment.DeleteCommentOutputData;

/**
 * Tests for the delete comment interactor.
 */
public class DeleteCommentInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successDeletesCommentWrittenByUser() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        dao.saveComment(makeComment());

        final DeleteCommentOutputBoundary presenter =
                new DeleteCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            DeleteCommentOutputData outputData) {
                        assertEquals(true, outputData.isDeleted());
                        assertFalse(dao.existsByCommentId("comment-1"));
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Delete comment should have succeeded.");
                        return errorMessage;
                    }
                };

        final DeleteCommentInputBoundary interactor =
                new DeleteCommentInteractor(dao, presenter);
        interactor.execute(new DeleteCommentInputData("comment-1", "elodie"));
    }

    @Test
    void failureWhenDifferentUserTriesToDelete() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        dao.saveComment(makeComment());

        final DeleteCommentOutputBoundary presenter =
                new DeleteCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            DeleteCommentOutputData outputData) {
                        fail("Delete comment should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Comment could not be deleted.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final DeleteCommentInputBoundary interactor =
                new DeleteCommentInteractor(dao, presenter);
        interactor.execute(new DeleteCommentInputData("comment-1", "lily"));
    }

    @Test
    void failureWhenCommentIdIsEmpty() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final DeleteCommentOutputBoundary presenter =
                new DeleteCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            DeleteCommentOutputData outputData) {
                        fail("Delete comment should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Comment id cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final DeleteCommentInputBoundary interactor =
                new DeleteCommentInteractor(dao, presenter);
        interactor.execute(new DeleteCommentInputData(" ", "elodie"));
    }

    private Comment makeComment() {
        return new Comment("comment-1", "review-1", null, "elodie",
                "Elodie", "I agree.", TIME, new HashSet<>());
    }
}
