package interface_adapter.media_reviews;

import java.util.ArrayList;
import java.util.List;

import use_case.review.ReviewSummaryData;
import use_case.review.create_review.CreateReviewOutputBoundary;
import use_case.review.create_review.CreateReviewOutputData;
import use_case.review.delete_review.DeleteReviewOutputBoundary;
import use_case.review.delete_review.DeleteReviewOutputData;
import use_case.review.edit_review.EditReviewOutputBoundary;
import use_case.review.edit_review.EditReviewOutputData;
import use_case.review.get_media_reviews.GetMediaReviewsOutputBoundary;
import use_case.review.get_media_reviews.GetMediaReviewsOutputData;
import use_case.review.like_review.LikeReviewOutputBoundary;
import use_case.review.like_review.LikeReviewOutputData;
import use_case.review.unlike_review.UnlikeReviewOutputBoundary;
import use_case.review.unlike_review.UnlikeReviewOutputData;

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
     * Converts review summaries into rows that can be displayed by the media
     * reviews panel.
     */
    public List<MediaReviewRow> prepareReviews(
            final List<ReviewSummaryData> reviews) {
        final List<MediaReviewRow> reviewRows = new ArrayList<>();
        if (reviews != null) {
            for (ReviewSummaryData review : reviews) {
                if (review != null) {
                    reviewRows.add(createReviewRow(review));
                }
            }
        }
        return reviewRows;
    }

    /**
     * Converts an error message into display-safe text.
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
     * Converts one review summary into one displayed row.
     */
    private MediaReviewRow createReviewRow(final ReviewSummaryData review) {
        return new MediaReviewRow(review.getReviewId(),
                review.getAuthorUsername(), review.getAuthorDisplayName(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount(), review.getSource());
    }

    /**
     * Checks whether a text value is empty or only whitespace.
     */
    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

}
