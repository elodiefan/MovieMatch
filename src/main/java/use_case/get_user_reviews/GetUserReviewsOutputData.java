package use_case.get_user_reviews;

import java.util.ArrayList;
import java.util.List;
import java.time.ZonedDateTime;

/**
 * Output data for loading reviews written by one user.
 */
public final class GetUserReviewsOutputData {
    /** The reviews. */
    private final List<UserReviewData> reviews;

    /**
     * Creates output data for loaded user reviews.
     * @param inputReviews the loaded user reviews
     */
    public GetUserReviewsOutputData(final List<UserReviewData>
                                            inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /**
     * Returns loaded user reviews.
     * @return the loaded user reviews
     */
    public List<UserReviewData> getReviews() {
        return new ArrayList<>(reviews);
    }

    /**
     * One review row prepared by the user reviews use case.
     */
    public static final class UserReviewData {
        private final String reviewId;
        private final int mediaId;
        private final String mediaType;
        private final String mediaTitle;
        private final double rating;
        private final String reviewText;
        private final ZonedDateTime createdAt;
        private final ZonedDateTime updatedAt;
        private final int likeCount;

        /**
         * Creates one user review row.
         * @param reviewId the review id
         * @param mediaId the media id
         * @param mediaType the media type
         * @param mediaTitle the media title
         * @param rating the review rating
         * @param reviewText the review text
         * @param createdAt when the review was created
         * @param updatedAt when the review was last updated
         * @param likeCount the number of likes
         */
        public UserReviewData(final String reviewId, final int mediaId,
                              final String mediaType,
                              final String mediaTitle, final double rating,
                              final String reviewText,
                              final ZonedDateTime createdAt,
                              final ZonedDateTime updatedAt,
                              final int likeCount) {
            this.reviewId = reviewId;
            this.mediaId = mediaId;
            this.mediaType = mediaType;
            this.mediaTitle = mediaTitle;
            this.rating = rating;
            this.reviewText = reviewText;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.likeCount = likeCount;
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

        public double getRating() {
            return rating;
        }

        public String getReviewText() {
            return reviewText;
        }

        public ZonedDateTime getCreatedAt() {
            return createdAt;
        }

        public ZonedDateTime getUpdatedAt() {
            return updatedAt;
        }

        public int getLikeCount() {
            return likeCount;
        }
    }
}
