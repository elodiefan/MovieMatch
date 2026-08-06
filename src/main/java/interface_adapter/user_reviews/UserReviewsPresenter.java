package interface_adapter.user_reviews;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Review;
import use_case.comment.UserCommentSummaryData;

/**
 * Presenter for the user reviews view.
 */
public class UserReviewsPresenter {
    /**
     * Converts review entities into rows that can be displayed by the user
     * reviews view.
     * @param reviews the reviews to present
     * @return display-safe review rows
     */
    public List<UserReviewRow> prepareReviews(final List<Review> reviews) {
        final List<UserReviewRow> reviewRows = new ArrayList<>();
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
     * Converts comment summaries into rows for the user's comments tab.
     * @param comments the comment summaries to present
     * @return display-safe comment rows
     */
    public List<UserReviewsState.CommentRow> prepareComments(
            final List<UserCommentSummaryData> comments) {
        final List<UserReviewsState.CommentRow> commentRows =
                new ArrayList<>();
        if (comments != null) {
            for (UserCommentSummaryData comment : comments) {
                if (comment != null) {
                    commentRows.add(createCommentRow(comment));
                }
            }
        }
        return commentRows;
    }

    /**
     * Converts an error message into display-safe text.
     * @param errorMessage the error message to present
     * @return the display-safe error message
     */
    public String prepareFailView(final String errorMessage) {
        final String displayError;
        if (isBlank(errorMessage)) {
            displayError = "Unable to load reviews.";
        } else {
            displayError = errorMessage.trim();
        }
        return displayError;
    }

    /**
     * Converts one review entity into one displayed row.
     * @param review the review to convert
     * @return the displayed review row
     */
    private UserReviewRow createReviewRow(final Review review) {
        return new UserReviewRow(review.getReviewId(), review.getMediaId(),
                review.getMediaType(), review.getMediaTitle(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount());
    }

    /**
     * Converts one comment summary into one displayed comment row.
     * @param comment the comment summary to convert
     * @return the displayed comment row
     */
    private UserReviewsState.CommentRow createCommentRow(
            final UserCommentSummaryData comment) {
        return new UserReviewsState.CommentRow(comment.getCommentId(),
                comment.getReviewId(), comment.getMediaTitle(),
                comment.getReviewText(), comment.getCommentText(),
                comment.getCreatedAt(), comment.getLikeCount());
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
     * Display data for one review written by the user.
     */
    public static final class UserReviewRow {
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
         * Creates display data for one user review row.
         * @param reviewId the review id
         * @param mediaId the reviewed media id
         * @param mediaType the reviewed media type
         * @param mediaTitle the reviewed media title
         * @param rating the review rating percentage
         * @param reviewText the review text
         * @param createdAt the review creation time
         * @param updatedAt the review update time
         * @param likeCount the number of likes on the review
         */
        public UserReviewRow(final String reviewId, final int mediaId,
                             final String mediaType, final String mediaTitle,
                             final double rating, final String reviewText,
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

        /**
         * Returns the review id.
         * @return the review id
         */
        public String getReviewId() {
            return reviewId;
        }

        /**
         * Returns the reviewed media id.
         * @return the media id
         */
        public int getMediaId() {
            return mediaId;
        }

        /**
         * Returns the reviewed media type.
         * @return the media type
         */
        public String getMediaType() {
            return mediaType;
        }

        /**
         * Returns the reviewed media title.
         * @return the media title
         */
        public String getMediaTitle() {
            return mediaTitle;
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
