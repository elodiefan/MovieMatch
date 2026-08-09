package use_case.review.delete_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DeleteReviewDataTest {

    @Test
    void inputDataReturnsReviewAndUsername() {
        final DeleteReviewInputData data = new DeleteReviewInputData("review-1", "bob");

        assertEquals("review-1", data.getReviewId());
        assertEquals("bob", data.getUsername());
    }

    @Test
    void outputDataReturnsDeletedStatus() {
        assertTrue(new DeleteReviewOutputData(true).isDeleted());
    }
}
