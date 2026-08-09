package use_case.review.unlike_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UnlikeReviewDataTest {

    @Test
    void inputDataReturnsReviewAndUsername() {
        final UnlikeReviewInputData data = new UnlikeReviewInputData("review-1", "bob");

        assertEquals("review-1", data.getReviewId());
        assertEquals("bob", data.getUsername());
    }

    @Test
    void outputDataReturnsUnlikedStatus() {
        assertTrue(new UnlikeReviewOutputData(true).isUnliked());
    }
}
