package use_case.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import data_access.InMemoryCommentDataAccessObject;
import entity.Comment;
import org.junit.jupiter.api.Test;
import use_case.comment.create_comment.CreateCommentInputBoundary;
import use_case.comment.create_comment.CreateCommentInputData;
import use_case.comment.create_comment.CreateCommentInteractor;
import use_case.comment.create_comment.CreateCommentOutputBoundary;
import use_case.comment.create_comment.CreateCommentOutputData;

/**
 * Tests for the create comment interactor.
 */
public class CreateCommentInteractorTest {

    @Test
    void successCreatesAndSavesTopLevelComment() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final CreateCommentInputData inputData = new CreateCommentInputData(
                "review-1", null, "elodie", "Elodie", "I agree.");

        final CreateCommentOutputBoundary presenter =
                new CreateCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            CreateCommentOutputData outputData) {
                        final Comment comment = outputData.getComment();
                        assertNotNull(comment.getCommentId());
                        assertEquals("review-1", comment.getReviewId());
                        assertEquals(null, comment.getParentCommentId());
                        assertEquals("elodie", comment.getAuthorUsername());
                        assertEquals("Elodie",
                                comment.getAuthorDisplayName());
                        assertEquals("I agree.", comment.getCommentText());
                        assertTrue(dao.existsByCommentId(
                                comment.getCommentId()));
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Create comment should have succeeded.");
                        return errorMessage;
                    }
                };

        final CreateCommentInputBoundary interactor =
                new CreateCommentInteractor(dao, presenter);
        interactor.execute(inputData);
    }

    @Test
    void successTrimsParentCommentIdForReply() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final CreateCommentInputData inputData = new CreateCommentInputData(
                " review-1 ", " parent-1 ", " elodie ", " Elodie ",
                " I agree. ");

        final CreateCommentOutputBoundary presenter =
                new CreateCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            CreateCommentOutputData outputData) {
                        final Comment comment = outputData.getComment();
                        assertEquals("review-1", comment.getReviewId());
                        assertEquals("parent-1",
                                comment.getParentCommentId());
                        assertEquals("elodie", comment.getAuthorUsername());
                        assertEquals("Elodie",
                                comment.getAuthorDisplayName());
                        assertEquals("I agree.", comment.getCommentText());
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        fail("Create comment should have succeeded.");
                        return errorMessage;
                    }
                };

        final CreateCommentInputBoundary interactor =
                new CreateCommentInteractor(dao, presenter);
        interactor.execute(inputData);
    }

    @Test
    void failureWhenCommentTextIsEmpty() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final CreateCommentInputData inputData = new CreateCommentInputData(
                "review-1", null, "elodie", "Elodie", " ");

        final CreateCommentOutputBoundary presenter =
                new CreateCommentOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            CreateCommentOutputData outputData) {
                        fail("Create comment should have failed.");
                    }

                    @Override
                    public String prepareFailView(String errorMessage) {
                        assertEquals("Comment text cannot be empty.",
                                errorMessage);
                        return errorMessage;
                    }
                };

        final CreateCommentInputBoundary interactor =
                new CreateCommentInteractor(dao, presenter);
        interactor.execute(inputData);
    }
}
