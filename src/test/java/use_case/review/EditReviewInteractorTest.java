package use_case.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;

import data_access.InMemoryReviewDataAccessObject;
import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.edit_review.EditReviewInputBoundary;
import use_case.review.edit_review.EditReviewInputData;
import use_case.review.edit_review.EditReviewInteractor;
import use_case.review.edit_review.EditReviewOutputBoundary;
import use_case.review.edit_review.EditReviewOutputData;

/**
 * Tests for the edit review interactor.
 */
public class EditReviewInteractorTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void successEditsReviewWrittenByUser() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final Review review = makeReview();
        dao.saveReview(review);

        final EditReviewOutputBoundary presenter = new EditReviewOutputBoundary() {
            @Override
            public void prepareSuccessView(EditReviewOutputData outputData) {
                assertEquals("review-1", outputData.getReview().getReviewId());
                assertEquals(75.0, outputData.getReview().getRating());
                assertEquals("Updated.", outputData.getReview().getReviewText());
                assertEquals(75.0,
                        dao.getReviewById("review-1").get().getRating());
            }

            @Override
            public String prepareFailView(String errorMessage) {
                fail("Edit review should have succeeded.");
                return errorMessage;
            }
        };

        final EditReviewInputBoundary interactor =
                new EditReviewInteractor(dao, presenter);
        interactor.execute(new EditReviewInputData("review-1", "elodie",
                75.0, "Updated."));
    }

    @Test
    void failureWhenDifferentUserTriesToEdit() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview());

        final EditReviewOutputBoundary presenter = new EditReviewOutputBoundary() {
            @Override
            public void prepareSuccessView(EditReviewOutputData outputData) {
                fail("Edit review should have failed.");
            }

            @Override
            public String prepareFailView(String errorMessage) {
                assertEquals("Review could not be edited.", errorMessage);
                return errorMessage;
            }
        };

        final EditReviewInputBoundary interactor =
                new EditReviewInteractor(dao, presenter);
        interactor.execute(new EditReviewInputData("review-1", "lily",
                75.0, "Updated."));
    }

    @Test
    void failureWhenRatingIsOutOfRange() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview());

        final EditReviewOutputBoundary presenter = new EditReviewOutputBoundary() {
            @Override
            public void prepareSuccessView(EditReviewOutputData outputData) {
                fail("Edit review should have failed.");
            }

            @Override
            public String prepareFailView(String errorMessage) {
                assertEquals("Rating must be between 0 and 100.",
                        errorMessage);
                return errorMessage;
            }
        };

        final EditReviewInputBoundary interactor =
                new EditReviewInteractor(dao, presenter);
        interactor.execute(new EditReviewInputData("review-1", "elodie",
                -1.0, "Updated."));
    }

    private Review makeReview() {
        return new Review("review-1", 550, "movie", "Fight Club",
                "elodie", "Elodie", 90.0, "Great.", TIME, TIME,
                "moviematch", new HashSet<>());
    }
}
