package use_case.review.create_review;

/**
 * Input data for creating a review.
 */
public final class CreateReviewInputData {
    /**
     * The media id.
     */
    private final int mediaId;
    /**
     * The media type.
     */
    private final String mediaType;
    /**
     * The media title.
     */
    private final String mediaTitle;
    /**
     * The release year.
     */
    private final int releaseYear;
    /**
     * The poster path.
     */
    private final String posterPath;
    /**
     * The author username.
     */
    private final String authorUsername;
    /**
     * The author display name.
     */
    private final String authorDisplayName;
    /**
     * The rating.
     */
    private final double rating;
    /**
     * The review text.
     */
    private final String reviewText;

    /**
     * Creates input data for creating a review.
     * @param inputMediaId the reviewed media id
     * @param inputMediaType the reviewed media type
     * @param inputMediaTitle the reviewed media title
     * @param inputReleaseYear the reviewed media release year
     * @param inputPosterPath the reviewed media poster path
     * @param inputAuthorUsername the author's username
     * @param inputAuthorDisplayName the author's display name
     * @param inputRating the rating percentage
     * @param inputReviewText the review text
     */
    public CreateReviewInputData(final int inputMediaId,
                                 final String inputMediaType,
                                 final String inputMediaTitle,
                                 final int inputReleaseYear,
                                 final String inputPosterPath,
                                 final String inputAuthorUsername,
                                 final String inputAuthorDisplayName,
                                 final double inputRating,
                                 final String inputReviewText) {
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
        this.mediaTitle = inputMediaTitle;
        this.releaseYear = inputReleaseYear;
        this.posterPath = inputPosterPath;
        this.authorUsername = inputAuthorUsername;
        this.authorDisplayName = inputAuthorDisplayName;
        this.rating = inputRating;
        this.reviewText = inputReviewText;
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

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public double getRating() {
        return rating;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public String getReviewText() {
        return reviewText;
    }
}
