package use_case.log_media;

/** Input boundary for logging media to user lists. */
public interface LogMediaInputBoundary {

    /** Adds a media item to the current user's watchlist. */
    void addToWatchlist(LogMediaInputData inputData);

    /** Adds a media item to the current user's watch history. */
    void addToWatchHistory(LogMediaInputData inputData);
}
