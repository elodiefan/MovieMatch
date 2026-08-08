package use_case.get_user_comments;

import java.util.ArrayList;
import java.util.List;
import java.time.ZonedDateTime;

/**
 * Output data for loading comments written by one user.
 */
public final class GetUserCommentsOutputData {
    /** The comments. */
    private final List<UserCommentData> comments;

    /**
     * Creates output data for loaded user comments.
     * @param inputComments the loaded user comments
     */
    public GetUserCommentsOutputData(
            final List<UserCommentData> inputComments) {
        this.comments = new ArrayList<>(inputComments);
    }

    /**
     * Returns loaded user comment summaries.
     * @return the loaded user comment summaries
     */
    public List<UserCommentData> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * One comment row prepared by the get user comments use case.
     */
    public static final class UserCommentData {
        private final String commentId;
        private final String reviewId;
        private final int mediaId;
        private final String mediaType;
        private final String mediaTitle;
        private final int releaseYear;
        private final String posterPath;
        private final String reviewText;
        private final String commentText;
        private final ZonedDateTime createdAt;
        private final int likeCount;

        /**
         * Creates one user comment row.
         * @param commentId the comment id
         * @param reviewId the review id
         * @param mediaId the media id
         * @param mediaType the media type
         * @param mediaTitle the media title
         * @param releaseYear the release year
         * @param posterPath the poster path
         * @param reviewText the review text
         * @param commentText the comment text
         * @param createdAt when the comment was created
         * @param likeCount the number of likes
         */
        public UserCommentData(final String commentId, final String reviewId,
                               final int mediaId, final String mediaType,
                               final String mediaTitle,
                               final int releaseYear,
                               final String posterPath,
                               final String reviewText,
                               final String commentText,
                               final ZonedDateTime createdAt,
                               final int likeCount) {
            this.commentId = commentId;
            this.reviewId = reviewId;
            this.mediaId = mediaId;
            this.mediaType = mediaType;
            this.mediaTitle = mediaTitle;
            this.releaseYear = releaseYear;
            this.posterPath = posterPath;
            this.reviewText = reviewText;
            this.commentText = commentText;
            this.createdAt = createdAt;
            this.likeCount = likeCount;
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
}
