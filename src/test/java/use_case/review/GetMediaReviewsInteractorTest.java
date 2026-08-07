package use_case.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import data_access.InMemoryReviewDataAccessObject;
import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.get_media_reviews.GetMediaReviewsInputBoundary;
import use_case.review.get_media_reviews.GetMediaReviewsInputData;
import use_case.review.get_media_reviews.GetMediaReviewsInteractor;
import use_case.review.get_media_reviews.GetMediaReviewsOutputBoundary;
import use_case.review.get_media_reviews.GetMediaReviewsOutputData;

/**
 * Tests for the get media reviews interactor.
 */
public class GetMediaReviewsInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successReturnsMediaReviewsNewestFirst() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview("old", 550, "movie", TIME));
        dao.saveReview(makeReview("wrong-type", 550, "tv",
                TIME.plusHours(1)));
        dao.saveReview(makeReview("new", 550, "movie", TIME.plusHours(2)));
        dao.saveReview(makeReview("wrong-media", 551, "movie",
                TIME.plusHours(3)));

        final GetMediaReviewsOutputBoundary presenter =
                new GetMediaReviewsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetMediaReviewsOutputData outputData) {
                        final List<Review> reviews = outputData.getReviews();
                        assertEquals(2, reviews.size());
                        assertEquals("new", reviews.get(0).getReviewId());
                        assertEquals("old", reviews.get(1).getReviewId());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Get media reviews should have succeeded.");
                        return errorMessage;
                    }
                };

        final GetMediaReviewsInputBoundary interactor =
                new GetMediaReviewsInteractor(dao, presenter);
        interactor.execute(new GetMediaReviewsInputData(550, "movie"));
    }

    @Test
    void failureWhenMediaTypeIsEmpty() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final GetMediaReviewsOutputBoundary presenter =
                new GetMediaReviewsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetMediaReviewsOutputData outputData) {
                        fail("Get media reviews should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Media type cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final GetMediaReviewsInputBoundary interactor =
                new GetMediaReviewsInteractor(dao, presenter);
        interactor.execute(new GetMediaReviewsInputData(550, " "));
    }

    private Review makeReview(String reviewId, int mediaId, String mediaType,
                              ZonedDateTime createdAt) {
        return new Review(reviewId, mediaId, mediaType, "Media", "elodie",
                "Elodie", 90.0, "Great.", createdAt, createdAt,
                "moviematch", new HashSet<>());
    }
}
