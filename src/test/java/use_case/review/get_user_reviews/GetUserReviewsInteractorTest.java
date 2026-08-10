package use_case.review.get_user_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Review;

class GetUserReviewsInteractorTest {

    @Test
    void validUsernameLoadsAndSortsNewestFirst() {
        final ZonedDateTime earlier = ZonedDateTime.parse(
                "2026-08-09T10:00:00-04:00[America/Toronto]");
        final Review first = review("review-1", earlier);
        final Review second = review("review-2", earlier.plusMinutes(5));
        final RecordingPresenter presenter = new RecordingPresenter();

        new GetUserReviewsInteractor(username -> new ArrayList<>(List.of(first, second)), presenter)
                .execute(new GetUserReviewsInputData(" bob "));

        assertEquals("review-2", presenter.success.getReviews().get(0).getReviewId());
        assertEquals("review-1", presenter.success.getReviews().get(1).getReviewId());
        assertNull(presenter.failure);
    }

    @Test
    void blankUsernamePresentsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new GetUserReviewsInteractor(username -> List.of(), presenter).execute(new GetUserReviewsInputData("  "));

        assertEquals("Username cannot be empty.", presenter.failure);
    }

    private Review review(String id, ZonedDateTime time) {
        return new Review(id, 101, "movie", "Example Movie", 2026, "/poster.jpg",
                "bob", "Bob", 8.0, "Review", time, time, "user", Set.of());
    }

    private static final class RecordingPresenter implements GetUserReviewsOutputBoundary {
        private GetUserReviewsOutputData success;
        private String failure;

        @Override
        public void prepareUserReviewsSuccessView(GetUserReviewsOutputData outputData) {
            success = outputData;
        }

        @Override
        public String prepareFailView(String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
