package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import data_access.InMemoryCommentDataAccessObject;
import entity.Comment;
import org.junit.jupiter.api.Test;
import use_case.comment.get_review_comments.GetReviewCommentsInputBoundary;
import use_case.comment.get_review_comments.GetReviewCommentsInputData;
import use_case.comment.get_review_comments.GetReviewCommentsInteractor;
import use_case.comment.get_review_comments.GetReviewCommentsOutputBoundary;
import use_case.comment.get_review_comments.GetReviewCommentsOutputData;

/**
 * Tests for the get review comments interactor.
 */
public class GetReviewCommentsInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successReturnsReviewCommentsOldestFirst() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        dao.saveComment(makeComment("old", "review-1", "elodie", TIME));
        dao.saveComment(makeComment("other", "review-2", "lily",
                TIME.plusHours(1)));
        dao.saveComment(makeComment("new", "review-1", "yidan",
                TIME.plusHours(2)));

        final GetReviewCommentsOutputBoundary presenter =
                new GetReviewCommentsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetReviewCommentsOutputData outputData) {
                        final List<Comment> comments =
                                outputData.getComments();
                        assertEquals("review-1", outputData.getReviewId());
                        assertEquals(2, comments.size());
                        assertEquals("old", comments.get(0).getCommentId());
                        assertEquals("new", comments.get(1).getCommentId());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Get review comments should have succeeded.");
                        return errorMessage;
                    }
                };

        final GetReviewCommentsInputBoundary interactor =
                new GetReviewCommentsInteractor(dao, presenter);
        interactor.execute(new GetReviewCommentsInputData("review-1"));
    }

    @Test
    void failureWhenReviewIdIsEmpty() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final GetReviewCommentsOutputBoundary presenter =
                new GetReviewCommentsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetReviewCommentsOutputData outputData) {
                        fail("Get review comments should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Review id cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final GetReviewCommentsInputBoundary interactor =
                new GetReviewCommentsInteractor(dao, presenter);
        interactor.execute(new GetReviewCommentsInputData(" "));
    }

    private Comment makeComment(String commentId, String reviewId,
                                String username, ZonedDateTime createdAt) {
        return new Comment(commentId, reviewId, null, username, username,
                "I agree.", createdAt, new HashSet<>());
    }
}
