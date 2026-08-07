package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

import data_access.InMemoryCommentDataAccessObject;
import entity.Comment;
import org.junit.jupiter.api.Test;
import use_case.comment.unlike_comment.UnlikeCommentInputBoundary;
import use_case.comment.unlike_comment.UnlikeCommentInputData;
import use_case.comment.unlike_comment.UnlikeCommentInteractor;
import use_case.comment.unlike_comment.UnlikeCommentOutputBoundary;
import use_case.comment.unlike_comment.UnlikeCommentOutputData;

/**
 * Tests for the unlike comment interactor.
 */
public class UnlikeCommentInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successUnlikesExistingComment() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final Comment comment = makeComment();
        comment.like("lily");
        dao.saveComment(comment);

        final UnlikeCommentOutputBoundary presenter =
                new UnlikeCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            UnlikeCommentOutputData outputData) {
                        assertEquals(true, outputData.isUnliked());
                        assertEquals(0, dao.getCommentById("comment-1").get()
                                .getLikeCount());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Unlike comment should have succeeded.");
                        return errorMessage;
                    }
                };

        final UnlikeCommentInputBoundary interactor =
                new UnlikeCommentInteractor(dao, presenter);
        interactor.execute(new UnlikeCommentInputData("comment-1", "lily"));
    }

    @Test
    void successOutputFalseWhenCommentDoesNotExist() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final UnlikeCommentOutputBoundary presenter =
                new UnlikeCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            UnlikeCommentOutputData outputData) {
                        assertEquals(false, outputData.isUnliked());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Missing comment should return false, not fail.");
                        return errorMessage;
                    }
                };

        final UnlikeCommentInputBoundary interactor =
                new UnlikeCommentInteractor(dao, presenter);
        interactor.execute(new UnlikeCommentInputData("missing", "lily"));
    }

    @Test
    void failureWhenCommentIdIsEmpty() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final UnlikeCommentOutputBoundary presenter =
                new UnlikeCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            UnlikeCommentOutputData outputData) {
                        fail("Unlike comment should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Comment id cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final UnlikeCommentInputBoundary interactor =
                new UnlikeCommentInteractor(dao, presenter);
        interactor.execute(new UnlikeCommentInputData(" ", "lily"));
    }

    private Comment makeComment() {
        return new Comment("comment-1", "review-1", null, "elodie",
                "Elodie", "I agree.", TIME, new HashSet<>());
    }
}
