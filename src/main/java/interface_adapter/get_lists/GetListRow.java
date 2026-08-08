package interface_adapter.get_lists;

/**
 * Display row for one saved media item.
 */
public final class GetListRow {

    private final int mediaId;
    private final String mediaType;
    private final String mediaTitle;
    private final String loggedAt;
    private final String posterPath;

    /**
     * Creates a saved media display row.
     * @param inputMediaId the media id
     * @param inputMediaType the media type
     * @param inputMediaTitle the media title
     * @param inputLoggedAt the logged date
     * @param inputPosterPath the poster path
     */
    public GetListRow(final int inputMediaId,
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
