package use_case.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class SearchOutputDataTest {

    @Test
    void gettersExposeCompleteFreshSearchResult() {
        final List<MediaResultData> results = List.of(mediaResult(1, "Arrival"));
        final SearchOutputData outputData = new SearchOutputData(
                results, "arrival", 4, true, false, 83);

        assertSame(results, outputData.getResults());
        assertEquals("arrival", outputData.getKeyword());
        assertEquals(4, outputData.getNextPage());
        assertTrue(outputData.isMoreAvailable());
        assertFalse(outputData.isAppending());
        assertEquals(83, outputData.getTotalResults());
    }

    @Test
    void gettersExposeCompletedAppendResult() {
        final SearchOutputData outputData = new SearchOutputData(
                List.of(), "dark", 8, false, true, 20);

        assertFalse(outputData.isMoreAvailable());
        assertTrue(outputData.isAppending());
        assertEquals(8, outputData.getNextPage());
        assertEquals(20, outputData.getTotalResults());
    }

    private static MediaResultData mediaResult(int id, String title) {
        return new MediaResultData(id, "movie", title, 2016, 8.0,
                List.of("Science Fiction"), List.of(878), "en",
                "Overview for " + title, "/poster.jpg");
    }
}
