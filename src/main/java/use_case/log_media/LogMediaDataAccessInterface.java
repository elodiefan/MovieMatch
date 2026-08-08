package use_case.log_media;

/**
 * Data access interface for logging media to user lists.
 */
public interface LogMediaDataAccessInterface {

    /**
     * Returns the username of the logged-in user.
     * @return the current username
     */
    String getCurrentUsername();

    /**
     * Adds a media item to a user's watchlist.
     * @param username the username
     * @param mediaId the media id
     * @param mediaType the media type
     * @param mediaTitle the media title
     * @param posterPath the poster path
     * @param addedAt the Toronto timestamp when the item was added
     */
    void addToWatchlist(String username, int mediaId, String mediaType,
                        String mediaTitle, String posterPath, String addedAt);

    /**
     * Adds a media item to a user's watch history.
     * @param username the username
     * @param mediaId the media id
     * @param mediaType the media type
     * @param mediaTitle the media title
     * @param posterPath the poster path
     * @param watchedAt the Toronto timestamp when the item was watched
     */
    void addToWatchHistory(String username, int mediaId, String mediaType,
                           String mediaTitle, String posterPath, String watchedAt);
}
