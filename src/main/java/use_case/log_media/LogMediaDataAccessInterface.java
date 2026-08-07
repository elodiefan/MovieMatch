package use_case.log_media;

/** Data access interface for logging media to user lists. */
public interface LogMediaDataAccessInterface {

    /** Returns the username of the logged-in user. */
    String getCurrentUsername();

    /** Adds a media item to a user's watchlist. */
    void addToWatchlist(String username, int mediaId, String mediaType,
                        String mediaTitle, String addedAt);

    /** Adds a media item to a user's watch history. */
    void addToWatchHistory(String username, int mediaId, String mediaType,
                           String mediaTitle, String watchedAt);
}
