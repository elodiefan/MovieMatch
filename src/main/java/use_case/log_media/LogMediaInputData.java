package use_case.log_media;

/**
 * Input data for logging a media item to a user list.
 */
public class LogMediaInputData {
    /** The media id. */
    private final int mediaId;
    /** The media type. */
    private final String mediaType;
    /** The media title. */
    private final String mediaTitle;

    /**
     * Creates input data for one media item.
     * @param inputMediaId the media id
     * @param inputMediaType the media type
     * @param inputMediaTitle the media title
     */
    public LogMediaInputData(final int inputMediaId,
                             final String inputMediaType,
                             final String inputMediaTitle) {
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
        this.mediaTitle = inputMediaTitle;
    }

    /**
     * Returns the media id.
     * @return the media id
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Returns the media type.
     * @return the media type
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Returns the media title.
     * @return the media title
     */
    public String getMediaTitle() {
        return mediaTitle;
    }
}
