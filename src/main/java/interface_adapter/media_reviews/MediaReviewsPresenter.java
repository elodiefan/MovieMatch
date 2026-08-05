package interface_adapter.media_reviews;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Review;

/**
 * Presenter for the media reviews panel.
 */
public class MediaReviewsPresenter {
    /**
     * Converts review entities into rows that can be displayed by the media
     * reviews panel.
     * @param reviews the reviews to present
     * @return display-safe media review rows
     */
    public List<MediaReviewRow> prepareReviews(final List<Review> reviews) {
        final List<MediaReviewRow> reviewRows = new ArrayList<>();
        if (reviews != null) {
            for (Review review : reviews) {
                if (review != null) {
                    reviewRows.add(createReviewRow(review));
                }
            }
        }
        return reviewRows;
    }

    /**
     * Converts an error message into display-safe text.
     * @param errorMessage the error message to present
     * @return the display-safe error message
     */
    public String prepareFailView(final String errorMessage) {
        final String displayError;
        if (isBlank(errorMessage)) {
            displayError = "Unable to load media reviews.";
        } else {
            displayError = errorMessage.trim();
        }
        return displayError;
    }

    /**
     * Converts one review entity into one displayed row.
     * @param review the review to convert
     * @return the displayed media review row
     */
    private MediaReviewRow createReviewRow(final Review review) {
        return new MediaReviewRow(review.getReviewId(),
                review.getAuthorUsername(), review.getAuthorDisplayName(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount());
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Display data for one community review on a media page.
     */
    public static final class MediaReviewRow {
        private final String reviewId;
        private final String authorUsername;
        private final String authorDisplayName;
        private final double rating;
        private final String reviewText;
        private final ZonedDateTime createdAt;
        private final ZonedDateTime updatedAt;
        private final int likeCount;

        /**
         * Creates display data for one media review row.
         * @param reviewId the review id
         * @param authorUsername the author's username
         * @param authorDisplayName the author's display name
         * @param rating the review rating percentage
         * @param reviewText the review text
         * @param createdAt the review creation time
         * @param updatedAt the review update time
         * @param likeCount the number of likes on the review
         */
        public MediaReviewRow(final String reviewId,
                              final String authorUsername,
                              final String authorDisplayName,
                              final double rating, final String reviewText,
                              final ZonedDateTime createdAt,
                              final ZonedDateTime updatedAt,
                              final int likeCount) {
            this.reviewId = reviewId;
            this.authorUsername = authorUsername;
            this.authorDisplayName = authorDisplayName;
            this.rating = rating;
            this.reviewText = reviewText;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.likeCount = likeCount;
        }

        /**
         * Returns the review id.
         * @return the review id
         */
        public String getReviewId() {
            return reviewId;
        }

        /**
         * Returns the author's username.
         * @return the author's username
         */
        public String getAuthorUsername() {
            return authorUsername;
        }

        /**
         * Returns the author's display name.
         * @return the author's display name
         */
        public String getAuthorDisplayName() {
            return authorDisplayName;
        }

        /**
         * Returns the review rating percentage.
         * @return the rating percentage
         */
        public double getRating() {
            return rating;
        }

        /**
         * Returns the review text.
         * @return the review text
         */
        public String getReviewText() {
            return reviewText;
        }

        /**
         * Returns the review creation time.
         * @return the creation time
         */
        public ZonedDateTime getCreatedAt() {
            return createdAt;
        }

        /**
         * Returns the review update time.
         * @return the update time
         */
        public ZonedDateTime getUpdatedAt() {
            return updatedAt;
        }

        /**
         * Returns the review like count.
         * @return the like count
         */
        public int getLikeCount() {
            return likeCount;
        }
    }
}
