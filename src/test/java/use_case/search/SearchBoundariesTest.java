package use_case.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Media;
import entity.Movie;

class SearchBoundariesTest {

    @Test
    void mediaDataAccessCanBeUsedThroughSearchableContract() {
        final SearchMediaDataAccess dataAccess = new OneMovieDataAccess();
        final Searchable<Media> searchable = dataAccess;

        assertEquals("arrival", searchable.search("arrival").get(0).getTitle());
        assertEquals(2, dataAccess.searchPage("arrival", 2).getTotalPages());
    }

    @Test
    void inputAndOutputBoundariesSupportInteractorContract() {
        final RecordingOutputBoundary output = new RecordingOutputBoundary();
        final SearchInputBoundary input = new SearchInteractor(
                new OneMovieDataAccess(), output);

        input.execute(new SearchInputData("arrival"));

        assertEquals("arrival", output.success.getKeyword());
        assertTrue(output.failure == null);
    }

    private static class OneMovieDataAccess implements SearchMediaDataAccess {
        @Override
        public List<Media> search(String keyword) {
            return searchPage(keyword, 1).getMedia();
        }

        @Override
        public MediaPage searchPage(String keyword, int page) {
            final Movie movie = new Movie(page, keyword, 2016, 8.0,
                    new ArrayList<>(), "en", new ArrayList<>(), 116);
            return new MediaPage(List.of(movie), 2, 2);
        }
    }

    private static class RecordingOutputBoundary
            implements SearchOutputBoundary {
        private SearchOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(SearchOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            failure = error;
        }
    }
}
