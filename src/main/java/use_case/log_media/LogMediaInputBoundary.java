package use_case.log_media;

/**
 * Input boundary for logging media to user lists.
 */
public interface LogMediaInputBoundary {

    /**
     * Adds a media item to the current user's watchlist.
     * @param inputData the media item to add
     */
    void addToWatchlist(LogMediaInputData inputData);

    /**
     * Adds a media item to the current user's watch history.
     * @param inputData the media item to add
     */
    void addToWatchHistory(LogMediaInputData inputData);
}
