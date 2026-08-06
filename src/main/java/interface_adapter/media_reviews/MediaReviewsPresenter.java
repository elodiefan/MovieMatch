package interface_adapter.media_reviews;

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
        return new MediaReviewRow(review.getReviewId(),
                review.getAuthorUsername(), review.getAuthorDisplayName(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount(), review.getSource());
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     * @param value the value to check
     * @return true if the value is blank
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

}
