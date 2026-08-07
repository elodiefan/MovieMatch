package interface_adapter.account;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The state for the reviews view opened from a user's account.
 */
public class ReviewsState {
    private String username = "";
    private List<ReviewRow> reviews = new ArrayList<>();
    private List<CommentRow> comments = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ReviewRow> getReviews() {
        return new ArrayList<>(reviews);
    }

    public void setReviews(List<ReviewRow> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }

    public List<CommentRow> getComments() {
        return new ArrayList<>(comments);
    }

    public void setComments(List<CommentRow> comments) {
        this.comments = new ArrayList<>(comments);
    }

    /**
     * Display data for one review in the user's review history.
     */
    public static class ReviewRow {
        private final String mediaTitle;
        private final double rating;
        private final String reviewText;
        private final ZonedDateTime createdAt;

        public ReviewRow(String mediaTitle, double rating, String reviewText,
                         ZonedDateTime createdAt) {
            this.mediaTitle = mediaTitle;
            this.rating = rating;
            this.reviewText = reviewText;
            this.createdAt = createdAt;
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
    }

    /**
     * Display data for one comment in the user's comment history.
     */
    public static class CommentRow {
        private final String mediaTitle;
        private final String reviewText;
        private final String commentText;
        private final ZonedDateTime createdAt;

        public CommentRow(String mediaTitle, String reviewText, String commentText,
                          ZonedDateTime createdAt) {
            this.mediaTitle = mediaTitle;
            this.reviewText = reviewText;
            this.commentText = commentText;
            this.createdAt = createdAt;
        }

        public String getMediaTitle() {
            return mediaTitle;
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
    }
}
