package interface_adapter.get_lists;

/**
 * Display row for one saved media item.
 */
public final class GetListRow {

    private final String mediaTitle;
    private final String loggedAt;
    private final String posterPath;

    /**
     * Creates a saved media display row.
     * @param inputMediaTitle the media title
     * @param inputLoggedAt the logged date
     * @param inputPosterPath the poster path
     */
    public GetListRow(final String inputMediaTitle,
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
