package use_case.review.get_media_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetMediaReviewsInputDataTest {

    @Test
    void inputDataReturnsMediaInformation() {
        final GetMediaReviewsInputData data = new GetMediaReviewsInputData(101, "movie");

        assertEquals(101, data.getMediaId());
        assertEquals("movie", data.getMediaType());
    }
}
