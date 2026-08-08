package use_case.get_watch_history;

/**
 * Output data for one watch history item.
 */
public final class WatchHistoryItemData {

    private final String mediaTitle;
    private final String loggedAt;
    private final String posterPath;

    /**
     * Creates watch history item output data.
     * @param inputMediaTitle the media title
     * @param inputLoggedAt the logged date
     * @param inputPosterPath the poster path
     */
    public WatchHistoryItemData(final String inputMediaTitle,
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
