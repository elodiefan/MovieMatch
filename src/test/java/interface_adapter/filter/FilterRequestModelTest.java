package interface_adapter.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;

import interface_adapter.search_result.SearchResultRow;

class FilterRequestModelTest {

    @Test
    void gettersReturnAllCollectedFilterInput() {
        final List<SearchResultRow> results = List.of(row());
        final List<String> languages = List.of("en", "fr");
        final List<Integer> genres = List.of(18, 878);
        final FilterRequestModel model = new FilterRequestModel(results,
                languages, 7.5, genres, 2000, 2025);

        assertSame(results, model.getOriginalResults());
        assertSame(languages, model.getLanguages());
        assertEquals(7.5, model.getMinimumRating());
        assertSame(genres, model.getGenreIds());
        assertEquals(2000, model.getEarliestYear());
        assertEquals(2025, model.getLatestYear());
    }

    private static SearchResultRow row() {
        return new SearchResultRow(1, "movie", "Arrival", 2016, 7.9,
                List.of("Drama"), List.of(18), "en", "Overview", "/a.jpg");
    }
}
