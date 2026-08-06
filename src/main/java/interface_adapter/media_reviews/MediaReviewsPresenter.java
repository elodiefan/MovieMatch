package interface_adapter.media_reviews;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.Review;
import use_case.review.CreateReviewOutputBoundary;
import use_case.review.CreateReviewOutputData;
import use_case.review.DeleteReviewOutputBoundary;
import use_case.review.DeleteReviewOutputData;
import use_case.review.EditReviewOutputBoundary;
import use_case.review.EditReviewOutputData;
import use_case.review.GetMediaReviewsOutputBoundary;
import use_case.review.GetMediaReviewsOutputData;
import use_case.review.LikeReviewOutputBoundary;
import use_case.review.LikeReviewOutputData;
import use_case.review.UnlikeReviewOutputBoundary;
import use_case.review.UnlikeReviewOutputData;

/**
 * Presenter for the media reviews panel.
 */
public final class MediaReviewsPresenter
        implements GetMediaReviewsOutputBoundary, CreateReviewOutputBoundary,
        EditReviewOutputBoundary, DeleteReviewOutputBoundary,
        LikeReviewOutputBoundary, UnlikeReviewOutputBoundary {
    /** The media reviews view model. */
    private final MediaReviewsViewModel mediaReviewsViewModel;

    /**
     * Creates a presenter used only for row conversion.
     */
    public MediaReviewsPresenter() {
        this(null);
    }

    /**
     * Creates a presenter for the media reviews view model.
     * @param inputMediaReviewsViewModel the view model to update
     */
    public MediaReviewsPresenter(
            final MediaReviewsViewModel inputMediaReviewsViewModel) {
        this.mediaReviewsViewModel = inputMediaReviewsViewModel;
    }

    @Override
    public void prepareSuccessView(final GetMediaReviewsOutputData outputData) {
        final MediaReviewsState state = mediaReviewsViewModel.getState();
        state.setReviews(prepareReviews(outputData.getReviews()));
        state.setMediaReviewsError(null);
        mediaReviewsViewModel.setState(state);
        mediaReviewsViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(final CreateReviewOutputData outputData) {
        clearError();
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
        if (mediaReviewsViewModel != null) {
            final MediaReviewsState state = mediaReviewsViewModel.getState();
            state.setMediaReviewsError(displayError);
            mediaReviewsViewModel.setState(state);
            mediaReviewsViewModel.firePropertyChanged();
        }
        return displayError;
    }

    private void clearError() {
        if (mediaReviewsViewModel != null) {
            final MediaReviewsState state = mediaReviewsViewModel.getState();
            state.setMediaReviewsError(null);
            mediaReviewsViewModel.setState(state);
            mediaReviewsViewModel.firePropertyChanged();
        }
    }

    /**
     * Converts one review entity into one displayed row.
     * @param review the review to convert
     * @return the displayed media review row
     */
    private MediaReviewRow createReviewRow(final Review review) {
        return new MediaReviewRow(review);
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
        /** The review id. */
        private final String reviewId;
        /** The author username. */
        private final String authorUsername;
        /** The author display name. */
        private final String authorDisplayName;
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
         * Creates display data for one media review row.
         * @param review the review to present
         */
        public MediaReviewRow(final Review review) {
            this.reviewId = review.getReviewId();
            this.authorUsername = review.getAuthorUsername();
            this.authorDisplayName = review.getAuthorDisplayName();
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
