package interface_adapter.user_reviews;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Review;
import use_case.comment.GetUserCommentsOutputBoundary;
import use_case.comment.GetUserCommentsOutputData;
import use_case.comment.UserCommentSummaryData;
import use_case.review.DeleteReviewOutputBoundary;
import use_case.review.DeleteReviewOutputData;
import use_case.review.EditReviewOutputBoundary;
import use_case.review.EditReviewOutputData;
import use_case.review.GetUserReviewsOutputBoundary;
import use_case.review.GetUserReviewsOutputData;
import use_case.review.LikeReviewOutputBoundary;
import use_case.review.LikeReviewOutputData;
import use_case.review.UnlikeReviewOutputBoundary;
import use_case.review.UnlikeReviewOutputData;

/**
 * Presenter for the user reviews view.
 */
public final class UserReviewsPresenter implements GetUserReviewsOutputBoundary,
        GetUserCommentsOutputBoundary, EditReviewOutputBoundary,
        DeleteReviewOutputBoundary, LikeReviewOutputBoundary,
        UnlikeReviewOutputBoundary {
    /** The user reviews view model. */
    private final UserReviewsViewModel userReviewsViewModel;

    /**
     * Creates a presenter for the user reviews view.
     * @param inputUserReviewsViewModel the view model to update
     */
    public UserReviewsPresenter(
            final UserReviewsViewModel inputUserReviewsViewModel) {
        this.userReviewsViewModel = inputUserReviewsViewModel;
    }

    /**
     * Prepares loaded reviews for display.
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(final GetUserReviewsOutputData outputData) {
        final UserReviewsState state = userReviewsViewModel.getState();
        state.setReviews(prepareReviews(outputData.getReviews()));
        state.setUserReviewsError(null);
        userReviewsViewModel.setState(state);
        userReviewsViewModel.firePropertyChanged();
    }

    /**
     * Prepares loaded comments for display.
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(
            final GetUserCommentsOutputData outputData) {
        final UserReviewsState state = userReviewsViewModel.getState();
        state.setComments(prepareComments(outputData.getComments()));
        state.setUserReviewsError(null);
        userReviewsViewModel.setState(state);
        userReviewsViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(final EditReviewOutputData outputData) {
        clearError();
    }

    @Override
    public void prepareSuccessView(final DeleteReviewOutputData outputData) {
        clearError();
    }

    @Override
    public void prepareSuccessView(final LikeReviewOutputData outputData) {
        clearError();
    }

    @Override
    public void prepareSuccessView(final UnlikeReviewOutputData outputData) {
        clearError();
    }

    /**
     * Converts review entities into rows that can be displayed by the user
     * reviews view.
     * @param reviews the reviews to present
     * @return display-safe review rows
     */
    private List<UserReviewRow> prepareReviews(final List<Review> reviews) {
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
    private List<UserReviewsState.CommentRow> prepareComments(
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
        final UserReviewsState state = userReviewsViewModel.getState();
        state.setUserReviewsError(displayError);
        userReviewsViewModel.setState(state);
        userReviewsViewModel.firePropertyChanged();
        return displayError;
    }

    private void clearError() {
        final UserReviewsState state = userReviewsViewModel.getState();
        state.setUserReviewsError(null);
        userReviewsViewModel.setState(state);
        userReviewsViewModel.firePropertyChanged();
    }

    /**
     * Converts one review entity into one displayed row.
     * @param review the review to convert
     * @return the displayed review row
     */
    private UserReviewRow createReviewRow(final Review review) {
        return new UserReviewRow(review);
    }

    /**
     * Converts one comment summary into one displayed comment row.
     * @param comment the comment summary to convert
     * @return the displayed comment row
     */
    private UserReviewsState.CommentRow createCommentRow(
            final UserCommentSummaryData comment) {
        return new UserReviewsState.CommentRow(comment);
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
        /** The review id. */
        private final String reviewId;
        /** The media id. */
        private final int mediaId;
        /** The media type. */
        private final String mediaType;
        /** The media title. */
        private final String mediaTitle;
        /** The rating. */
        private final double rating;
        /** The review text. */
        private final String reviewText;
        /** The created at. */
        private final ZonedDateTime createdAt;
        /** The updated at. */
        private final ZonedDateTime updatedAt;
        /** The like count. */
        private final int likeCount;

        /**
         * Creates display data for one user review row.
         * @param review the review to present
         */
        public UserReviewRow(final Review review) {
            this.reviewId = review.getReviewId();
            this.mediaId = review.getMediaId();
            this.mediaType = review.getMediaType();
            this.mediaTitle = review.getMediaTitle();
            this.rating = review.getRating();
            this.reviewText = review.getReviewText();
            this.createdAt = review.getCreatedAt();
            this.updatedAt = review.getUpdatedAt();
            this.likeCount = review.getLikeCount();
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
