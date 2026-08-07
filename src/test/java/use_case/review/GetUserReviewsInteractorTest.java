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
import use_case.review.get_user_reviews.GetUserReviewsInputBoundary;
import use_case.review.get_user_reviews.GetUserReviewsInputData;
import use_case.review.get_user_reviews.GetUserReviewsInteractor;
import use_case.review.get_user_reviews.GetUserReviewsOutputBoundary;
import use_case.review.get_user_reviews.GetUserReviewsOutputData;

/**
 * Tests for the get user reviews interactor.
 */
public class GetUserReviewsInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successReturnsUserReviewsNewestFirst() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview("old", "elodie", TIME));
        dao.saveReview(makeReview("other", "lily", TIME.plusHours(1)));
        dao.saveReview(makeReview("new", "elodie", TIME.plusHours(2)));

        final GetUserReviewsOutputBoundary presenter =
                new GetUserReviewsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetUserReviewsOutputData outputData) {
                        final List<Review> reviews = outputData.getReviews();
                        assertEquals(2, reviews.size());
                        assertEquals("new", reviews.get(0).getReviewId());
                        assertEquals("old", reviews.get(1).getReviewId());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Get user reviews should have succeeded.");
                        return errorMessage;
                    }
                };

        final GetUserReviewsInputBoundary interactor =
                new GetUserReviewsInteractor(dao, presenter);
        interactor.execute(new GetUserReviewsInputData("elodie"));
    }

    @Test
    void failureWhenUsernameIsEmpty() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final GetUserReviewsOutputBoundary presenter =
                new GetUserReviewsOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            GetUserReviewsOutputData outputData) {
                        fail("Get user reviews should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Username cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final GetUserReviewsInputBoundary interactor =
                new GetUserReviewsInteractor(dao, presenter);
        interactor.execute(new GetUserReviewsInputData(" "));
    }

    private Review makeReview(String reviewId, String username,
                              ZonedDateTime createdAt) {
        return new Review(reviewId, 550, "movie", "Fight Club", username,
                username, 90.0, "Great.", createdAt, createdAt,
                "moviematch", new HashSet<>());
    }
}
