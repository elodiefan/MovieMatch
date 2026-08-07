package use_case.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

import data_access.InMemoryReviewDataAccessObject;
import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.unlike_review.UnlikeReviewInputBoundary;
import use_case.review.unlike_review.UnlikeReviewInputData;
import use_case.review.unlike_review.UnlikeReviewInteractor;
import use_case.review.unlike_review.UnlikeReviewOutputBoundary;
import use_case.review.unlike_review.UnlikeReviewOutputData;

/**
 * Tests for the unlike review interactor.
 */
public class UnlikeReviewInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successUnlikesExistingReview() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final Review review = makeReview();
        review.like("lily");
        dao.saveReview(review);

        final UnlikeReviewOutputBoundary presenter =
                new UnlikeReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            UnlikeReviewOutputData outputData) {
                        assertEquals(true, outputData.isUnliked());
                        assertEquals(0, dao.getReviewById("review-1").get()
                                .getLikeCount());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Unlike review should have succeeded.");
                        return errorMessage;
                    }
                };

        final UnlikeReviewInputBoundary interactor =
                new UnlikeReviewInteractor(dao, presenter);
        interactor.execute(new UnlikeReviewInputData("review-1", "lily"));
    }

    @Test
    void successOutputFalseWhenReviewDoesNotExist() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final UnlikeReviewOutputBoundary presenter =
                new UnlikeReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            UnlikeReviewOutputData outputData) {
                        assertEquals(false, outputData.isUnliked());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Missing review should return false, not fail.");
                        return errorMessage;
                    }
                };

        final UnlikeReviewInputBoundary interactor =
                new UnlikeReviewInteractor(dao, presenter);
        interactor.execute(new UnlikeReviewInputData("missing", "lily"));
    }

    @Test
    void failureWhenReviewIdIsEmpty() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final UnlikeReviewOutputBoundary presenter =
                new UnlikeReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            UnlikeReviewOutputData outputData) {
                        fail("Unlike review should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Review id cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final UnlikeReviewInputBoundary interactor =
                new UnlikeReviewInteractor(dao, presenter);
        interactor.execute(new UnlikeReviewInputData(" ", "lily"));
    }

    private Review makeReview() {
        return new Review("review-1", 550, "movie", "Fight Club",
                "elodie", "Elodie", 90.0, "Great.", TIME, TIME,
                "moviematch", new HashSet<>());
    }
}
