package use_case.review.get_media_reviews;

/** Input data for loading reviews for one media item. */
public final class GetMediaReviewsInputData {
    /** The media id. */
    private final int mediaId;
    /** The media type. */
    private final String mediaType;

    /** Handles this review or comment operation. */
    public GetMediaReviewsInputData(final int inputMediaId,
                                    final String inputMediaType) {
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
    }

    /** Handles this review or comment operation. */
    public int getMediaId() {
        return mediaId;
    }
    /** Handles this review or comment operation. */
    public String getMediaType() {
        return mediaType;
    }
}
