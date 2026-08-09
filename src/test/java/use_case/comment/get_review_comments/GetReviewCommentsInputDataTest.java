package use_case.comment.get_review_comments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetReviewCommentsInputDataTest {

    @Test
    void inputDataReturnsReviewId() {
        final GetReviewCommentsInputData data = new GetReviewCommentsInputData("review-1");

        assertEquals("review-1", data.getReviewId());
    }
}
