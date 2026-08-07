package interface_adapter.log_media;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import use_case.log_media.LogMediaInputBoundary;
import use_case.log_media.LogMediaInputData;

/**
 * Tests for the log media controller.
 */
class LogMediaControllerTest {

    @Test
    void addToWatchlistPackagesInputData() {
        final CapturingLogMediaInteractor interactor =
                new CapturingLogMediaInteractor();
        final LogMediaController controller = new LogMediaController(
                interactor);

        controller.addToWatchlist(550, "movie", "Fight Club");

        assertEquals("watchlist", interactor.calledMethod);
        assertEquals(550, interactor.inputData.getMediaId());
        assertEquals("movie", interactor.inputData.getMediaType());
        assertEquals("Fight Club", interactor.inputData.getMediaTitle());
    }

    @Test
    void addToWatchHistoryPackagesInputData() {
        final CapturingLogMediaInteractor interactor =
                new CapturingLogMediaInteractor();
        final LogMediaController controller = new LogMediaController(
                interactor);

        controller.addToWatchHistory(550, "movie", "Fight Club");

        assertEquals("history", interactor.calledMethod);
        assertEquals(550, interactor.inputData.getMediaId());
        assertEquals("movie", interactor.inputData.getMediaType());
        assertEquals("Fight Club", interactor.inputData.getMediaTitle());
    }

    private static final class CapturingLogMediaInteractor
            implements LogMediaInputBoundary {
        private String calledMethod = "";
        private LogMediaInputData inputData;

        @Override
        public void addToWatchlist(LogMediaInputData inputData) {
            calledMethod = "watchlist";
            this.inputData = inputData;
        }

        @Override
        public void addToWatchHistory(LogMediaInputData inputData) {
            calledMethod = "history";
            this.inputData = inputData;
        }
    }
}
