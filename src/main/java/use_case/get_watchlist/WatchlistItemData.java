package use_case.get_watchlist;

/**
 * Output data for one watchlist item.
 */
public final class WatchlistItemData {

    private final String mediaTitle;
    private final int mediaId;
    private final String mediaType;
    private final String loggedAt;
    private final String posterPath;

    /**
     * Creates watchlist item output data.
     * @param inputMediaId the media id
     * @param inputMediaType the media type
     * @param inputMediaTitle the media title
     * @param inputLoggedAt the logged date
     * @param inputPosterPath the poster path
     */
    public WatchlistItemData(final int inputMediaId,
                             final String inputMediaType,
                             final String inputMediaTitle,
                             final String inputLoggedAt,
                             final String inputPosterPath) {
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
        this.mediaTitle = inputMediaTitle;
        this.loggedAt = inputLoggedAt;
        this.posterPath = inputPosterPath;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public String getLoggedAt() {
        return loggedAt;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
