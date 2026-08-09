package use_case.review.get_user_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetUserReviewsInputDataTest {

    @Test
    void inputDataReturnsUsername() {
        final GetUserReviewsInputData data = new GetUserReviewsInputData("bob");

        assertEquals("bob", data.getUsername());
    }
}
