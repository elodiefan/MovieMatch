package use_case.log_media;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for {@link LogMediaInteractor}. */
class LogMediaInteractorTest {

    @Test
    void successAddsMediaToWatchlist() {
        final RecordingMediaDao dao = new RecordingMediaDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final LogMediaInteractor interactor = new LogMediaInteractor(dao, presenter);

        interactor.addToWatchlist(new LogMediaInputData(
                42, " movie ", " Example Movie ", " /poster.jpg "));

        assertTrue(dao.addedToWatchlist);
        assertEquals("bob", dao.usernameReceived);
        assertEquals("movie", dao.mediaTypeReceived);
        assertNotNull(dao.timestampReceived);
        assertEquals("Added to watchlist.", presenter.success.getMessage());
        assertNull(presenter.failure);
    }

    @Test
    void watchedMediaCannotBeAddedToWatchlist() {
        final RecordingMediaDao dao = new RecordingMediaDao();
        dao.alreadyWatched = true;
        final RecordingPresenter presenter = new RecordingPresenter();
        final LogMediaInteractor interactor = new LogMediaInteractor(dao, presenter);

        interactor.addToWatchlist(new LogMediaInputData(42, "movie", "Example Movie"));

        assertFalse(dao.addedToWatchlist);
        assertNull(presenter.success);
        assertEquals("This media is already in your watch history. Remove it from watch history "
                + "before adding it to your watchlist.", presenter.failure);
    }

    @Test
    void successAddsMediaToWatchHistory() {
        final RecordingMediaDao dao = new RecordingMediaDao();
        final RecordingPresenter presenter = new RecordingPresenter();

        new LogMediaInteractor(dao, presenter).addToWatchHistory(
                new LogMediaInputData(42, "movie", "Example Movie"));

        assertTrue(dao.addedToWatchHistory);
        assertEquals("Added to watch history.", presenter.success.getMessage());
        assertNull(presenter.failure);
    }

    @Test
    void nullMediaIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new LogMediaInteractor(new RecordingMediaDao(), presenter).addToWatchlist(null);
        assertEquals("No media selected.", presenter.failure);
    }

    @Test
    void loggedOutUserCannotSaveMedia() {
        final RecordingMediaDao dao = new RecordingMediaDao();
        dao.currentUsername = " ";
        final RecordingPresenter presenter = new RecordingPresenter();

        new LogMediaInteractor(dao, presenter).addToWatchHistory(
                new LogMediaInputData(42, "movie", "Example Movie"));

        assertEquals("Please log in before saving media.", presenter.failure);
    }

    @Test
    void missingMediaTypeIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new LogMediaInteractor(new RecordingMediaDao(), presenter).addToWatchHistory(
                new LogMediaInputData(42, " ", "Example Movie"));
        assertEquals("Media type is missing.", presenter.failure);
    }

    @Test
    void missingMediaTitleIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new LogMediaInteractor(new RecordingMediaDao(), presenter).addToWatchHistory(
                new LogMediaInputData(42, "movie", " "));
        assertEquals("Media title is missing.", presenter.failure);
    }

    private static final class RecordingMediaDao implements LogMediaDataAccessInterface {
        private boolean alreadyWatched;
        private boolean addedToWatchlist;
        private boolean addedToWatchHistory;
        private String currentUsername = "bob";
        private String usernameReceived;
        private String mediaTypeReceived;
        private String timestampReceived;

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public void addToWatchlist(final String username, final int mediaId,
                                   final String mediaType, final String mediaTitle,
                                   final String posterPath, final String addedAt) {
            addedToWatchlist = true;
            usernameReceived = username;
            mediaTypeReceived = mediaType;
            timestampReceived = addedAt;
        }

        @Override
        public void addToWatchHistory(final String username, final int mediaId,
                                      final String mediaType, final String mediaTitle,
                                      final String posterPath, final String watchedAt) {
            addedToWatchHistory = true;
        }

        @Override
        public boolean hasWatchedMedia(final String username, final int mediaId,
                                       final String mediaType) {
            return alreadyWatched;
        }
    }

    private static final class RecordingPresenter implements LogMediaOutputBoundary {
        private LogMediaOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(final LogMediaOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(final String error) {
            failure = error;
        }
    }
}
