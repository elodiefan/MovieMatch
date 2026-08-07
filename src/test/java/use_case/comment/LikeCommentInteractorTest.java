package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

import data_access.InMemoryCommentDataAccessObject;
import entity.Comment;
import org.junit.jupiter.api.Test;
import use_case.comment.like_comment.LikeCommentInputBoundary;
import use_case.comment.like_comment.LikeCommentInputData;
import use_case.comment.like_comment.LikeCommentInteractor;
import use_case.comment.like_comment.LikeCommentOutputBoundary;
import use_case.comment.like_comment.LikeCommentOutputData;

/**
 * Tests for the like comment interactor.
 */
public class LikeCommentInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successLikesExistingComment() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        dao.saveComment(makeComment());

        final LikeCommentOutputBoundary presenter =
                new LikeCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LikeCommentOutputData outputData) {
                        assertEquals(true, outputData.isLiked());
                        assertEquals(1, dao.getCommentById("comment-1").get()
                                .getLikeCount());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Like comment should have succeeded.");
                        return errorMessage;
                    }
                };

        final LikeCommentInputBoundary interactor =
                new LikeCommentInteractor(dao, presenter);
        interactor.execute(new LikeCommentInputData("comment-1", "lily"));
    }

    @Test
    void successOutputFalseWhenCommentDoesNotExist() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final LikeCommentOutputBoundary presenter =
                new LikeCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LikeCommentOutputData outputData) {
                        assertEquals(false, outputData.isLiked());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Missing comment should return false, not fail.");
                        return errorMessage;
                    }
                };

        final LikeCommentInputBoundary interactor =
                new LikeCommentInteractor(dao, presenter);
        interactor.execute(new LikeCommentInputData("missing", "lily"));
    }

    @Test
    void failureWhenUsernameIsEmpty() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final LikeCommentOutputBoundary presenter =
                new LikeCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LikeCommentOutputData outputData) {
                        fail("Like comment should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Username cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final LikeCommentInputBoundary interactor =
                new LikeCommentInteractor(dao, presenter);
        interactor.execute(new LikeCommentInputData("comment-1", " "));
    }

    private Comment makeComment() {
        return new Comment("comment-1", "review-1", null, "elodie",
                "Elodie", "I agree.", TIME, new HashSet<>());
    }
}
