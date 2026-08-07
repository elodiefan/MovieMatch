package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import data_access.InMemoryCommentDataAccessObject;
import data_access.InMemoryReviewDataAccessObject;
import entity.Comment;
import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.comment.get_user_comments.GetUserCommentsInputBoundary;
import use_case.comment.get_user_comments.GetUserCommentsInputData;
import use_case.comment.get_user_comments.GetUserCommentsInteractor;
import use_case.comment.get_user_comments.GetUserCommentsOutputBoundary;
import use_case.comment.get_user_comments.GetUserCommentsOutputData;

/**
 * Tests for the get user comments interactor.
 */
public class GetUserCommentsInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successReturnsUserCommentsNewestFirstWithReviewSummary() {
        final InMemoryCommentDataAccessObject commentDao =
                new InMemoryCommentDataAccessObject();
        final InMemoryReviewDataAccessObject reviewDao =
                new InMemoryReviewDataAccessObject();
        reviewDao.saveReview(makeReview());
        commentDao.saveComment(makeComment("old", "review-1", "elodie",
                TIME));
        commentDao.saveComment(makeComment("other", "review-1", "lily",
                TIME.plusHours(1)));
        commentDao.saveComment(makeComment("new", "review-1", "elodie",
                TIME.plusHours(2)));

        final GetUserCommentsOutputBoundary presenter =
                new GetUserCommentsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetUserCommentsOutputData outputData) {
                        final List<UserCommentSummaryData> comments =
                                outputData.getComments();
                        assertEquals(2, comments.size());
                        assertEquals("new", comments.get(0).getCommentId());
                        assertEquals("Fight Club",
                                comments.get(0).getMediaTitle());
                        assertEquals("Great movie.",
                                comments.get(0).getReviewText());
                        assertEquals("old", comments.get(1).getCommentId());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Get user comments should have succeeded.");
                        return errorMessage;
                    }
                };

        final GetUserCommentsInputBoundary interactor =
                new GetUserCommentsInteractor(commentDao, reviewDao,
                        presenter);
        interactor.execute(new GetUserCommentsInputData("elodie"));
    }

    @Test
    void failureWhenUsernameIsEmpty() {
        final InMemoryCommentDataAccessObject commentDao =
                new InMemoryCommentDataAccessObject();
        final InMemoryReviewDataAccessObject reviewDao =
                new InMemoryReviewDataAccessObject();
        final GetUserCommentsOutputBoundary presenter =
                new GetUserCommentsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetUserCommentsOutputData outputData) {
                        fail("Get user comments should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Username cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final GetUserCommentsInputBoundary interactor =
                new GetUserCommentsInteractor(commentDao, reviewDao,
                        presenter);
        interactor.execute(new GetUserCommentsInputData(" "));
    }

    private Review makeReview() {
        return new Review("review-1", 550, "movie", "Fight Club",
                "elodie", "Elodie", 90.0, "Great movie.", TIME, TIME,
                "moviematch", new HashSet<>());
    }

    private Comment makeComment(String commentId, String reviewId,
                                String username, ZonedDateTime createdAt) {
        return new Comment(commentId, reviewId, null, username, username,
                "I agree.", createdAt, new HashSet<>());
    }
}
