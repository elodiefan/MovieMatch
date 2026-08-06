package use_case.review;

/**
 * Input data for loading reviews for one media item.
 */
public class GetMediaReviewsInputData {
    private final int mediaId;
    private final String mediaType;

    public GetMediaReviewsInputData(final int mediaId,
                                    final String mediaType) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
    }

    public int getMediaId() { return mediaId; }
    public String getMediaType() { return mediaType; }
}
