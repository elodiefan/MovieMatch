package use_case.get_watchlist;

/**
 * Output data for one watchlist item.
 */
public final class WatchlistItemData {

    private final String mediaTitle;
    private final String loggedAt;
    private final String posterPath;

    /**
     * Creates watchlist item output data.
     * @param inputMediaTitle the media title
     * @param inputLoggedAt the logged date
     * @param inputPosterPath the poster path
     */
    public WatchlistItemData(final String inputMediaTitle,
                             final String inputLoggedAt,
                             final String inputPosterPath) {
        this.mediaTitle = inputMediaTitle;
        this.loggedAt = inputLoggedAt;
        this.posterPath = inputPosterPath;
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
