package use_case.log_media;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LogMediaDataTest {

    @Test
    void inputDataReturnsMediaInformation() {
        final LogMediaInputData data = new LogMediaInputData(
                101, "movie", "Example Movie", "/poster.jpg");

        assertEquals(101, data.getMediaId());
        assertEquals("movie", data.getMediaType());
        assertEquals("Example Movie", data.getMediaTitle());
        assertEquals("/poster.jpg", data.getPosterPath());
    }

    @Test
    void shortInputConstructorUsesEmptyPosterPath() {
        final LogMediaInputData data = new LogMediaInputData(101, "movie", "Example Movie");

        assertEquals("", data.getPosterPath());
    }

    @Test
    void outputDataReturnsTitleAndMessage() {
        final LogMediaOutputData data = new LogMediaOutputData(
                "Example Movie", "Added to watchlist");

        assertEquals("Example Movie", data.getMediaTitle());
        assertEquals("Added to watchlist", data.getMessage());
    }
}
