package use_case.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import use_case.search.MediaResultData;

/** Tests for {@link FilterInteractor}. */
class FilterInteractorTest {

    @Test
    void successKeepsOnlyMediaMatchingTheCriteria() {
        final MediaResultData match = media(1, "Match", 2024, 8.5, 18, "en");
        final MediaResultData lowRating = media(2, "Low", 2024, 6.0, 18, "en");
        final MediaResultData wrongLanguage = media(3, "French", 2024, 9.0, 18, "fr");
        final FilterCriteria criteria = new FilterCriteria(
                List.of("EN"), 8.0, List.of(18), 2020, 2025);
        final RecordingPresenter presenter = new RecordingPresenter();

        new FilterInteractor(presenter).execute(
                new FilterInputData(List.of(match, lowRating, wrongLanguage), criteria));

        assertNull(presenter.failure);
        assertEquals(List.of(match), presenter.success.getFilteredResults());
    }

    @Test
    void invalidYearRangeIsReportedAsFailure() {
        final FilterCriteria criteria = new FilterCriteria(
                null, null, null, 2025, 2020);
        final RecordingPresenter presenter = new RecordingPresenter();

        new FilterInteractor(presenter).execute(new FilterInputData(List.of(), criteria));

        assertNull(presenter.success);
        assertEquals("Earliest year cannot be later than latest year", presenter.failure);
    }

    private static MediaResultData media(final int id, final String title,
                                         final int year, final double rating,
                                         final int genreId, final String language) {
        return new MediaResultData(id, "movie", title, year, rating,
                List.of("Drama"), List.of(genreId), language, "Overview", "/poster.jpg");
    }

    private static final class RecordingPresenter implements FilterOutputBoundary {
        private FilterOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(final FilterOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(final String error) {
            failure = error;
        }
    }
}
