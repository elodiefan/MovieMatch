package use_case.log_media;

/**
 * Output data after logging a media item to a user list.
 */
public class LogMediaOutputData {
    /** The logged media title. */
    private final String mediaTitle;
    /** The success message. */
    private final String message;

    /**
     * Creates output data for a logged media item.
     */
    public LogMediaOutputData(final String inputMediaTitle,
                              final String inputMessage) {
        this.mediaTitle = inputMediaTitle;
        this.message = inputMessage;
    }

    /**
     * Returns the media title.
     */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /**
     * Returns the success message.
     */
    public String getMessage() {
        return message;
    }
}
