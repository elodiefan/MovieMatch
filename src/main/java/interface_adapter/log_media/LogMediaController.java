package interface_adapter.log_media;

import use_case.log_media.LogMediaInputBoundary;
import use_case.log_media.LogMediaInputData;

/** Controller for logging media to user lists. */
public class LogMediaController {
    /** The log media interactor. */
    private final LogMediaInputBoundary logMediaInteractor;

    /** Creates a controller for logging media. */
    public LogMediaController(
            final LogMediaInputBoundary inputLogMediaInteractor) {
        this.logMediaInteractor = inputLogMediaInteractor;
    }

    /** Adds the selected media item to the current user's watchlist. */
    public void addToWatchlist(final int mediaId, final String mediaType,
                               final String mediaTitle) {
        logMediaInteractor.addToWatchlist(new LogMediaInputData(mediaId,
                mediaType, mediaTitle));
    }

    /** Adds the selected media item to the current user's watch history. */
    public void addToWatchHistory(final int mediaId, final String mediaType,
                                  final String mediaTitle) {
        logMediaInteractor.addToWatchHistory(new LogMediaInputData(mediaId,
                mediaType, mediaTitle));
    }
}
