package use_case.review.get_media_reviews;

/**
 * Input data for loading reviews for one media item.
 */
public final class GetMediaReviewsInputData {
    /**
     * The media id.
     */
    private final int mediaId;
    /**
     * The media type.
     */
    private final String mediaType;

    /**
     * Handles this review or comment operation.
     * @param inputMediaId the inputMediaId
     * @param inputMediaType the inputMediaType
     */
    public GetMediaReviewsInputData(final int inputMediaId,
                                    final String inputMediaType) {
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getMediaType() {
        return mediaType;
    }
}
