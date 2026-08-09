package interface_adapter.user_reviews;

import java.time.ZonedDateTime;

/**
 * Display data for one comment in the user's comment history.
 */
public final class UserCommentRow {
    /**
     * The comment id.
     */
    private final String commentId;
    /**
     * The review id.
     */
    private final String reviewId;
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
     * The review text.
     */
    private final String reviewText;
    /**
     * The comment text.
     */
    private final String commentText;
    /**
     * The created at.
     */
    private final ZonedDateTime createdAt;
    /**
     * The like count.
     */
    private final int likeCount;

    /**
     * Creates display data for one comment row.
     * @param inputCommentId the comment id
     * @param inputReviewId the review id
     * @param inputMediaId the media id
     * @param inputMediaType the media type
     * @param inputMediaTitle the media title
     * @param inputReleaseYear the release year
     * @param inputPosterPath the poster path
     * @param inputReviewText the review text
     * @param inputCommentText the comment text
     * @param inputCreatedAt the creation time
     * @param inputLikeCount the like count
     */
    public UserCommentRow(final String inputCommentId,
                          final String inputReviewId,
                          final int inputMediaId,
                          final String inputMediaType,
                          final String inputMediaTitle,
                          final int inputReleaseYear,
                          final String inputPosterPath,
                          final String inputReviewText,
                          final String inputCommentText,
                          final ZonedDateTime inputCreatedAt,
                          final int inputLikeCount) {
        this.commentId = inputCommentId;
        this.reviewId = inputReviewId;
        this.mediaId = inputMediaId;
        this.mediaType = inputMediaType;
        this.mediaTitle = inputMediaTitle;
        this.releaseYear = inputReleaseYear;
        this.posterPath = inputPosterPath;
        this.reviewText = inputReviewText;
        this.commentText = inputCommentText;
        this.createdAt = inputCreatedAt;
        this.likeCount = inputLikeCount;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getReviewId() {
        return reviewId;
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getCommentText() {
        return commentText;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
