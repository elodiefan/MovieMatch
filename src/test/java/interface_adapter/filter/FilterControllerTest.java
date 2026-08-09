package interface_adapter.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import interface_adapter.search_result.SearchResultRow;
import use_case.filter.FilterCriteria;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;
import use_case.search.MediaResultData;

class FilterControllerTest {

    @Test
    void executeConvertsViewRequestIntoUseCaseInput() {
        final RecordingFilterInteractor interactor =
                new RecordingFilterInteractor();
        final FilterController controller = new FilterController(interactor);
        final SearchResultRow row = new SearchResultRow(101, "movie",
                "Arrival", 2016, 7.9, List.of("Drama", "Sci-Fi"),
                List.of(18, 878), "en", "First contact", "/arrival.jpg");

        controller.execute(new FilterRequestModel(List.of(row),
                List.of("en"), 7.0, List.of(18), 2000, 2020));

        assertNotNull(interactor.input);
        final FilterCriteria criteria = interactor.input.getCriteria();
        assertEquals(List.of("en"), criteria.getLanguages());
        assertEquals(7.0, criteria.getMinimumRating());
        assertEquals(List.of(18), criteria.getGenreIds());
        assertEquals(2000, criteria.getEarliestYear());
        assertEquals(2020, criteria.getLatestYear());

        final MediaResultData converted =
                interactor.input.getOriginalResults().get(0);
        assertEquals(101, converted.getMediaId());
        assertEquals("movie", converted.getMediaType());
        assertEquals("Arrival", converted.getTitle());
        assertEquals(2016, converted.getReleaseYear());
        assertEquals(7.9, converted.getAverageRating());
        assertEquals(List.of("Drama", "Sci-Fi"), converted.getGenreNames());
        assertEquals(List.of(18, 878), converted.getGenreIds());
        assertEquals("en", converted.getLanguage());
        assertEquals("First contact", converted.getOverview());
        assertEquals("/arrival.jpg", converted.getPosterPath());
    }

    @Test
    void executeSupportsAnEmptyOriginalResultSet() {
        final RecordingFilterInteractor interactor =
                new RecordingFilterInteractor();

        new FilterController(interactor).execute(new FilterRequestModel(
                List.of(), List.of(), null, List.of(), null, null));

        assertNotNull(interactor.input);
        assertEquals(List.of(), interactor.input.getOriginalResults());
    }

    private static class RecordingFilterInteractor
            implements FilterInputBoundary {
        private FilterInputData input;

        @Override
        public void execute(FilterInputData inputData) {
            input = inputData;
        }
    }
}
