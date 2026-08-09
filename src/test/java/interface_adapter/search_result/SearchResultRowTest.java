package interface_adapter.search_result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SearchResultRowTest {

    @Test
    void constructorValuesAreReturnedByGetters() {
        final SearchResultRow row = row();

        assertEquals(101, row.getMediaId());
        assertEquals("movie", row.getMediaType());
        assertEquals("Arrival", row.getTitle());
        assertEquals(2016, row.getReleaseYear());
        assertEquals(7.9, row.getAverageRating());
        assertEquals(List.of("Drama", "Science Fiction"),
                row.getGenreNames());
        assertEquals(List.of(18, 878), row.getGenreIds());
        assertEquals("en", row.getLanguage());
        assertEquals("First contact", row.getOverview());
        assertEquals("/arrival.jpg", row.getPosterPath());
    }

    @Test
    void genreListsAreDefensivelyCopiedOnInputAndOutput() {
        final List<String> names = new ArrayList<>(List.of("Drama"));
        final List<Integer> ids = new ArrayList<>(List.of(18));
        final SearchResultRow row = new SearchResultRow(1, "movie", "Title",
                2020, 8.0, names, ids, "en", "Overview", "/poster.jpg");
        names.clear();
        ids.clear();

        final List<String> returnedNames = row.getGenreNames();
        final List<Integer> returnedIds = row.getGenreIds();
        returnedNames.clear();
        returnedIds.clear();

        assertEquals(List.of("Drama"), row.getGenreNames());
        assertEquals(List.of(18), row.getGenreIds());
        assertNotSame(returnedNames, row.getGenreNames());
        assertNotSame(returnedIds, row.getGenreIds());
    }

    private static SearchResultRow row() {
        return new SearchResultRow(101, "movie", "Arrival", 2016, 7.9,
                List.of("Drama", "Science Fiction"), List.of(18, 878),
                "en", "First contact", "/arrival.jpg");
    }
}
