package use_case.review.create_review;

/** Input data for creating a review. */
public final class CreateReviewInputData {
    /** The media id. */
    private final int mediaId;
    /** The media type. */
    private final String mediaType;
    /** The media title. */
    private final String mediaTitle;
    /** The author username. */
    private final String authorUsername;
    /** The author display name. */
    private final String authorDisplayName;
    /** The rating. */
    private final double rating;
    /** The review text. */
    private final String reviewText;

    /** Creates input data for creating a review. */
    public CreateReviewInputData(final int inputMediaId,
                                 final String inputMediaType,
                                 final String inputMediaTitle,
                                 final String inputAuthorUsername,
                                 final String inputAuthorDisplayName,
                                 final double inputRating,
                                 final String inputReviewText) {
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
        this.mediaTitle = inputMediaTitle;
        this.authorUsername = inputAuthorUsername;
        this.authorDisplayName = inputAuthorDisplayName;
        this.rating = inputRating;
        this.reviewText = inputReviewText;
    }

    /** Handles this review or comment operation. */
    public int getMediaId() {
        return mediaId;
    }
    /** Handles this review or comment operation. */
    public String getMediaType() {
        return mediaType;
    }
    /** Handles this review or comment operation. */
    public String getMediaTitle() {
        return mediaTitle;
    }
    /** Handles this review or comment operation. */
    public String getAuthorUsername() {
        return authorUsername;
    }
    /** Handles this review or comment operation. */
    public String getAuthorDisplayName() {
        return authorDisplayName;
    }
    /** Handles this review or comment operation. */
    public double getRating() {
        return rating;
    }
    /** Handles this review or comment operation. */
    public String getReviewText() {
        return reviewText;
    }
}
