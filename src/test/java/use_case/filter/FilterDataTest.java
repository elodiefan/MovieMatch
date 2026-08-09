package use_case.filter;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;

import use_case.search.MediaResultData;

class FilterDataTest {

    @Test
    void inputDataReturnsResultsAndCriteria() {
        final List<MediaResultData> results = List.of();
        final FilterCriteria criteria = new FilterCriteria(
                List.of("en"), 7.0, List.of(18), 2000, 2026);
        final FilterInputData data = new FilterInputData(results, criteria);

        assertSame(results, data.getOriginalResults());
        assertSame(criteria, data.getCriteria());
    }

    @Test
    void outputDataReturnsFilteredResults() {
        final List<MediaResultData> results = List.of();
        final FilterOutputData data = new FilterOutputData(results);

        assertSame(results, data.getFilteredResults());
    }
}
