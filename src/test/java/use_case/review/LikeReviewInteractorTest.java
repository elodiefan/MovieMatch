package use_case.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

import data_access.InMemoryReviewDataAccessObject;
import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.like_review.LikeReviewInputBoundary;
import use_case.review.like_review.LikeReviewInputData;
import use_case.review.like_review.LikeReviewInteractor;
import use_case.review.like_review.LikeReviewOutputBoundary;
import use_case.review.like_review.LikeReviewOutputData;

/**
 * Tests for the like review interactor.
 */
public class LikeReviewInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successLikesExistingReview() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview());

        final LikeReviewOutputBoundary presenter =
                new LikeReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LikeReviewOutputData outputData) {
                        assertEquals(true, outputData.isLiked());
                        assertEquals(1, dao.getReviewById("review-1").get()
                                .getLikeCount());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Like review should have succeeded.");
                        return errorMessage;
                    }
                };

        final LikeReviewInputBoundary interactor =
                new LikeReviewInteractor(dao, presenter);
        interactor.execute(new LikeReviewInputData("review-1", "lily"));
    }

    @Test
    void successOutputFalseWhenReviewDoesNotExist() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final LikeReviewOutputBoundary presenter =
                new LikeReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LikeReviewOutputData outputData) {
                        assertEquals(false, outputData.isLiked());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Missing review should return false, not fail.");
                        return errorMessage;
                    }
                };

        final LikeReviewInputBoundary interactor =
                new LikeReviewInteractor(dao, presenter);
        interactor.execute(new LikeReviewInputData("missing", "lily"));
    }

    @Test
    void failureWhenUsernameIsEmpty() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final LikeReviewOutputBoundary presenter =
                new LikeReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            LikeReviewOutputData outputData) {
                        fail("Like review should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Username cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final LikeReviewInputBoundary interactor =
                new LikeReviewInteractor(dao, presenter);
        interactor.execute(new LikeReviewInputData("review-1", " "));
    }

    private Review makeReview() {
        return new Review("review-1", 550, "movie", "Fight Club",
                "elodie", "Elodie", 90.0, "Great.", TIME, TIME,
                "moviematch", new HashSet<>());
    }
}
