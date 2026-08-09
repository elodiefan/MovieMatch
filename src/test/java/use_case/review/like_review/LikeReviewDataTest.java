package use_case.review.like_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LikeReviewDataTest {

    @Test
    void inputDataReturnsReviewAndUsername() {
        final LikeReviewInputData data = new LikeReviewInputData("review-1", "bob");

        assertEquals("review-1", data.getReviewId());
        assertEquals("bob", data.getUsername());
    }

    @Test
    void outputDataReturnsLikedStatus() {
        assertTrue(new LikeReviewOutputData(true).isLiked());
    }
}
