package use_case.comment.get_user_comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import entity.Comment;
import entity.Review;

/** Tests for {@link GetUserCommentsInteractor}. */
class GetUserCommentsInteractorTest {

    @Test
    void successIncludesInformationFromTheRelatedReview() {
        final ZonedDateTime time = ZonedDateTime.parse("2026-01-01T12:00:00-05:00");
        final Comment comment = new Comment("comment-1", "review-1", null,
                "bob", "Bob", "Good point", time, new HashSet<>());
        final Review review = new Review("review-1", 42, "movie", "Example Movie",
                2025, "/poster.jpg", "alice", "Alice", 8.0, "Great movie",
                time, time, "MovieMatch", new HashSet<>());
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetUserCommentsInteractor interactor = new GetUserCommentsInteractor(
                username -> new ArrayList<>(List.of(comment)),
                reviewId -> Optional.of(review), presenter);

        interactor.execute("  bob  ");

        assertNotNull(presenter.success);
        assertNull(presenter.failure);
        final GetUserCommentsOutputData.UserCommentData result =
                presenter.success.getComments().get(0);
        assertEquals("comment-1", result.getCommentId());
        assertEquals("Example Movie", result.getMediaTitle());
        assertEquals("Good point", result.getCommentText());
    }

    @Test
    void blankUsernameIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final GetUserCommentsInteractor interactor = new GetUserCommentsInteractor(
                username -> new ArrayList<>(), reviewId -> Optional.empty(), presenter);

        interactor.execute("   ");

        assertNull(presenter.success);
        assertEquals("Username cannot be empty.", presenter.failure);
    }

    private static final class RecordingPresenter implements GetUserCommentsOutputBoundary {
        private GetUserCommentsOutputData success;
        private String failure;

        @Override
        public void prepareUserCommentsSuccessView(final GetUserCommentsOutputData outputData) {
            success = outputData;
        }

        @Override
        public String prepareFailView(final String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }
}
