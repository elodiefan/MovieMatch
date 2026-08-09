package use_case.review.get_media_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Review;

class GetMediaReviewsInteractorTest {

    @Test
    void validMediaLoadsAndSortsNewestFirst() {
        final ZonedDateTime earlier = ZonedDateTime.parse(
                "2026-08-09T10:00:00-04:00[America/Toronto]");
        final Review first = review("review-1", earlier);
        final Review second = review("review-2", earlier.plusMinutes(5));
        final RecordingPresenter presenter = new RecordingPresenter();

        new GetMediaReviewsInteractor((id, type) -> new ArrayList<>(List.of(first, second)), presenter)
                .execute(101, " movie ");

        assertEquals("review-2", presenter.success.getReviews().get(0).getReviewId());
        assertEquals("review-1", presenter.success.getReviews().get(1).getReviewId());
        assertNull(presenter.failure);
    }

    @Test
    void negativeMediaIdPresentsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new GetMediaReviewsInteractor((id, type) -> List.of(), presenter).execute(-1, "movie");

        assertEquals("Media id cannot be negative.", presenter.failure);
    }

    private Review review(String id, ZonedDateTime time) {
        return new Review(id, 101, "movie", "Example Movie", "bob", "Bob",
                8.0, "Review", time, time, "user", Set.of());
    }

    private static final class RecordingPresenter implements GetMediaReviewsOutputBoundary {
        private GetMediaReviewsOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(GetMediaReviewsOutputData outputData) {
            success = outputData;
        }

        @Override
        public String prepareFailView(String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
