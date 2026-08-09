package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Comment;
import use_case.comment.get_review_comments.GetReviewCommentsInteractor;
import use_case.comment.get_review_comments.GetReviewCommentsOutputBoundary;
import use_case.comment.get_review_comments.GetReviewCommentsOutputData;

class GetReviewCommentsInteractorTest {

    @Test
    void validReviewIdLoadsAndSortsComments() {
        final ZonedDateTime earlier = ZonedDateTime.parse(
                "2026-08-09T10:00:00-04:00[America/Toronto]");
        final Comment first = comment("comment-1", earlier);
        final Comment second = comment("comment-2", earlier.plusMinutes(5));
        final RecordingPresenter presenter = new RecordingPresenter();

        new GetReviewCommentsInteractor(reviewId -> new ArrayList<>(List.of(second, first)), presenter)
                .execute(" review-1 ");

        assertEquals("review-1", presenter.success.getReviewId().trim());
        assertEquals("comment-1", presenter.success.getComments().get(0).getCommentId());
        assertEquals("comment-2", presenter.success.getComments().get(1).getCommentId());
        assertNull(presenter.failure);
    }

    @Test
    void blankReviewIdPresentsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new GetReviewCommentsInteractor(reviewId -> List.of(), presenter).execute("  ");

        assertEquals("Review id cannot be empty.", presenter.failure);
    }

    private Comment comment(String id, ZonedDateTime time) {
        return new Comment(id, "review-1", null, "bob", "Bob",
                "Comment", time, Set.of());
    }

    private static final class RecordingPresenter implements GetReviewCommentsOutputBoundary {
        private GetReviewCommentsOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(GetReviewCommentsOutputData outputData) {
            success = outputData;
        }

        @Override
        public String prepareFailView(String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
