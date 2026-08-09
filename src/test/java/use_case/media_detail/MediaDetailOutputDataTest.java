package use_case.media_detail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class MediaDetailOutputDataTest {

    @Test
    void outputDataReturnsMediaInformation() {
        final List<String> genres = List.of("Drama");
        final MediaDetailOutputData data = new MediaDetailOutputData(
                101, "movie", "Example Movie", 2026, 8.5, genres,
                "en", "An example overview", "/poster.jpg");

        assertEquals(101, data.getMediaId());
        assertEquals("movie", data.getMediaType());
        assertEquals("Example Movie", data.getTitle());
        assertEquals(2026, data.getReleaseYear());
        assertEquals(8.5, data.getAverageRating());
        assertEquals(genres, data.getGenreNames());
        assertEquals("en", data.getLanguage());
        assertEquals("An example overview", data.getOverview());
        assertEquals("/poster.jpg", data.getPosterPath());
    }
}
