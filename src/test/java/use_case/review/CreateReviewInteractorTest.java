package use_case.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import data_access.InMemoryReviewDataAccessObject;
import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.create_review.CreateReviewInputBoundary;
import use_case.review.create_review.CreateReviewInputData;
import use_case.review.create_review.CreateReviewInteractor;
import use_case.review.create_review.CreateReviewOutputBoundary;
import use_case.review.create_review.CreateReviewOutputData;

/**
 * Tests for the create review interactor.
 */
public class CreateReviewInteractorTest {

    @Test
    void successCreatesAndSavesReview() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final CreateReviewInputData inputData = new CreateReviewInputData(550,
                "movie", "Fight Club", "elodie", "Elodie", 95.0,
                "Loved it.");

        final CreateReviewOutputBoundary presenter =
                new CreateReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            CreateReviewOutputData outputData) {
                        final Review review = outputData.getReview();
                        assertNotNull(review.getReviewId());
                        assertEquals(550, review.getMediaId());
                        assertEquals("movie", review.getMediaType());
                        assertEquals("Fight Club", review.getMediaTitle());
                        assertEquals("elodie", review.getAuthorUsername());
                        assertEquals("Elodie", review.getAuthorDisplayName());
                        assertEquals(95.0, review.getRating());
                        assertEquals("Loved it.", review.getReviewText());
                        assertTrue(dao.existsByReviewId(review.getReviewId()));
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Create review should have succeeded.");
                        return errorMessage;
                    }
                };

        final CreateReviewInputBoundary interactor =
                new CreateReviewInteractor(dao, presenter);
        interactor.execute(inputData);
    }

    @Test
    void failureWhenRatingIsOutOfRange() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final CreateReviewInputData inputData = new CreateReviewInputData(550,
                "movie", "Fight Club", "elodie", "Elodie", 101.0,
                "Loved it.");

        final CreateReviewOutputBoundary presenter =
                new CreateReviewOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            CreateReviewOutputData outputData) {
                        fail("Create review should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Rating must be between 0 and 100.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final CreateReviewInputBoundary interactor =
                new CreateReviewInteractor(dao, presenter);
        interactor.execute(inputData);
    }
}
