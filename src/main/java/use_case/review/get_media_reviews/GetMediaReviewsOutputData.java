package use_case.review.get_media_reviews;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.time.ZonedDateTime;

/**
 * Output data for loading reviews for one media item.
 */
public final class GetMediaReviewsOutputData {
    /** The reviews. */
    private final List<MediaReviewData> reviews;

    /**
     * Creates output data for loaded media reviews.
     * @param inputReviews the loaded media reviews
     */
    public GetMediaReviewsOutputData(final List<MediaReviewData>
                                             inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /**
     * Returns the loaded media reviews.
     * @return the loaded media reviews
     */
    public List<MediaReviewData> getReviews() {
        return new ArrayList<>(reviews);
    }

    /**
     * One review row prepared by the media reviews use case.
     */
    public static final class MediaReviewData {
        private final String reviewId;
        private final String authorUsername;
        private final String authorDisplayName;
        private final double rating;
        private final String reviewText;
        private final ZonedDateTime createdAt;
        private final ZonedDateTime updatedAt;
        private final int likeCount;
        private final Set<String> likedByUsernames;
        private final String source;

        /**
         * Creates one media review row.
         * @param reviewId the review id
         * @param authorUsername the author's username
         * @param authorDisplayName the author's display name
         * @param rating the review rating
         * @param reviewText the review text
         * @param createdAt when the review was created
         * @param updatedAt when the review was last updated
         * @param likeCount the number of likes
         * @param likedByUsernames usernames that liked the review
         * @param source the review source
         */
        public MediaReviewData(final String reviewId,
                               final String authorUsername,
                               final String authorDisplayName,
                               final double rating,
                               final String reviewText,
                               final ZonedDateTime createdAt,
                               final ZonedDateTime updatedAt,
                               final int likeCount,
                               final Set<String> likedByUsernames,
                               final String source) {
            this.reviewId = reviewId;
            this.authorUsername = authorUsername;
            this.authorDisplayName = authorDisplayName;
            this.rating = rating;
            this.reviewText = reviewText;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.likeCount = likeCount;
            this.likedByUsernames = new HashSet<>(likedByUsernames);
            this.source = source;
        }

        public String getReviewId() {
            return reviewId;
        }

        public String getAuthorUsername() {
            return authorUsername;
        }

        public String getAuthorDisplayName() {
            return authorDisplayName;
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

        public Set<String> getLikedByUsernames() {
            return new HashSet<>(likedByUsernames);
        }

        public String getSource() {
            return source;
        }
    }
}
