package use_case.media_detail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class MediaDetailInteractorTest {

    @Test
    void validInputPresentsMediaDetails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final MediaDetailInteractor interactor = new MediaDetailInteractor(presenter);
        final MediaDetailInputData inputData = new MediaDetailInputData(
                101, "movie", "Example Movie", 2026, 8.5,
                List.of("Drama"), "en", "An example overview", "/poster.jpg");

        interactor.execute(inputData);

        assertEquals(101, presenter.success.getMediaId());
        assertEquals("movie", presenter.success.getMediaType());
        assertEquals("Example Movie", presenter.success.getTitle());
        assertEquals(2026, presenter.success.getReleaseYear());
        assertEquals(8.5, presenter.success.getAverageRating());
        assertEquals(List.of("Drama"), presenter.success.getGenreNames());
        assertEquals("en", presenter.success.getLanguage());
        assertEquals("An example overview", presenter.success.getOverview());
        assertEquals("/poster.jpg", presenter.success.getPosterPath());
        assertNull(presenter.failure);
    }

    @Test
    void nullInputPresentsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new MediaDetailInteractor(presenter).execute(null);

        assertEquals("Unable to display media details.", presenter.failure);
        assertNull(presenter.success);
    }

    @Test
    void backActionReturnsToSearchResults() {
        final RecordingPresenter presenter = new RecordingPresenter();

        new MediaDetailInteractor(presenter).backToSearchResultView();

        assertTrue(presenter.returnedToSearchResults);
    }

    private static final class RecordingPresenter implements MediaDetailOutputBoundary {
        private MediaDetailOutputData success;
        private String failure;
        private boolean returnedToSearchResults;

        @Override
        public void prepareSuccessView(MediaDetailOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failure = errorMessage;
        }

        @Override
        public void backToSearchResultView() {
            returnedToSearchResults = true;
        }
    }
}
