package use_case.review;

/**
 * Input data for creating a review.
 */
public class CreateReviewInputData {
    private final int mediaId;
    private final String mediaType;
    private final String mediaTitle;
    private final String authorUsername;
    private final String authorDisplayName;
    private final double rating;
    private final String reviewText;

    /**
     * Creates input data for creating a review.
     * @param mediaId the reviewed media id
     * @param mediaType the reviewed media type
     * @param mediaTitle the reviewed media title
     * @param authorUsername the author's username
     * @param authorDisplayName the author's display name
     * @param rating the rating percentage
     * @param reviewText the review text
     */
    public CreateReviewInputData(final int mediaId, final String mediaType,
                                 final String mediaTitle,
                                 final String authorUsername,
                                 final String authorDisplayName,
                                 final double rating,
                                 final String reviewText) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.mediaTitle = mediaTitle;
        this.authorUsername = authorUsername;
        this.authorDisplayName = authorDisplayName;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public int getMediaId() { return mediaId; }
    public String getMediaType() { return mediaType; }
    public String getMediaTitle() { return mediaTitle; }
    public String getAuthorUsername() { return authorUsername; }
    public String getAuthorDisplayName() { return authorDisplayName; }
    public double getRating() { return rating; }
    public String getReviewText() { return reviewText; }
}
