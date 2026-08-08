package entity;

/**
 * A media item saved in a user's watchlist or watch history.
 */
public final class MediaListItem {

    private final int mediaId;
    private final String mediaType;
    private final String mediaTitle;
    private final String loggedAt;
    private final String posterPath;

    /**
     * Creates a saved media list item.
     * @param inputMediaId the media id
     * @param inputMediaType the media type
     * @param inputMediaTitle the media title
     * @param inputLoggedAt the date the item was saved
     * @param inputPosterPath the poster path
     */
    public MediaListItem(final int inputMediaId, final String inputMediaType,
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
