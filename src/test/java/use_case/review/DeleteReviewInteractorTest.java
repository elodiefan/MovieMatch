package use_case.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

import data_access.InMemoryReviewDataAccessObject;
import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.delete_review.DeleteReviewInputBoundary;
import use_case.review.delete_review.DeleteReviewInputData;
import use_case.review.delete_review.DeleteReviewInteractor;
import use_case.review.delete_review.DeleteReviewOutputBoundary;
import use_case.review.delete_review.DeleteReviewOutputData;

/**
 * Tests for the delete review interactor.
 */
public class DeleteReviewInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successDeletesReviewWrittenByUser() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview());

        final DeleteReviewOutputBoundary presenter =
                new DeleteReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            DeleteReviewOutputData outputData) {
                        assertEquals(true, outputData.isDeleted());
                        assertFalse(dao.existsByReviewId("review-1"));
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Delete review should have succeeded.");
                        return errorMessage;
                    }
                };

        final DeleteReviewInputBoundary interactor =
                new DeleteReviewInteractor(dao, presenter);
        interactor.execute(new DeleteReviewInputData("review-1", "elodie"));
    }

    @Test
    void failureWhenDifferentUserTriesToDelete() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview());

        final DeleteReviewOutputBoundary presenter =
                new DeleteReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            DeleteReviewOutputData outputData) {
                        fail("Delete review should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Review could not be deleted.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final DeleteReviewInputBoundary interactor =
                new DeleteReviewInteractor(dao, presenter);
        interactor.execute(new DeleteReviewInputData("review-1", "lily"));
    }

    @Test
    void failureWhenReviewIdIsEmpty() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final DeleteReviewOutputBoundary presenter =
                new DeleteReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            DeleteReviewOutputData outputData) {
                        fail("Delete review should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Review id cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final DeleteReviewInputBoundary interactor =
                new DeleteReviewInteractor(dao, presenter);
        interactor.execute(new DeleteReviewInputData(" ", "elodie"));
    }

    private Review makeReview() {
        return new Review("review-1", 550, "movie", "Fight Club",
                "elodie", "Elodie", 90.0, "Great.", TIME, TIME,
                "moviematch", new HashSet<>());
    }
}
