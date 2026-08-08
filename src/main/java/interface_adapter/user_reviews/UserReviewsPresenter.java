package interface_adapter.user_reviews;

import java.util.ArrayList;
import java.util.List;

import use_case.get_user_comments.GetUserCommentsOutputBoundary;
import use_case.get_user_comments.GetUserCommentsOutputData;
import use_case.delete_review.DeleteReviewOutputBoundary;
import use_case.edit_review.EditReviewOutputBoundary;
import use_case.edit_review.EditReviewOutputData;
import use_case.get_user_reviews.GetUserReviewsOutputBoundary;
import use_case.get_user_reviews.GetUserReviewsOutputData;
import use_case.like_review.LikeReviewOutputBoundary;
import use_case.unlike_review.UnlikeReviewOutputBoundary;

/**
 * Presenter for the user reviews view.
 */
public final class UserReviewsPresenter implements GetUserReviewsOutputBoundary,
        GetUserCommentsOutputBoundary, EditReviewOutputBoundary,
        DeleteReviewOutputBoundary, LikeReviewOutputBoundary,
        UnlikeReviewOutputBoundary {
    /**
     * The user reviews view model.
     */
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
    public void prepareUserReviewsSuccessView(
            final GetUserReviewsOutputData outputData) {
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
    public void prepareUserCommentsSuccessView(
            final GetUserCommentsOutputData outputData) {
        final UserReviewsState state = userReviewsViewModel.getState();
        state.setComments(prepareComments(outputData.getComments()));
        state.setUserReviewsError(null);
        userReviewsViewModel.setState(state);
        userReviewsViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(final EditReviewOutputData review) {
        clearError();
    }

    @Override
    public void prepareSuccessView(final boolean deleted) {
        clearError();
    }

    /**
     * Converts review summaries into rows that can be displayed by the user
     * reviews view.
     * @param reviews the reviews to present
     * @return display-safe review rows
     */
    private List<UserReviewRow> prepareReviews(
            final List<GetUserReviewsOutputData.UserReviewData> reviews) {
        final List<UserReviewRow> reviewRows = new ArrayList<>();
        if (reviews != null) {
            for (GetUserReviewsOutputData.UserReviewData review : reviews) {
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
    private List<UserCommentRow> prepareComments(
            final List<GetUserCommentsOutputData.UserCommentData> comments) {
        final List<UserCommentRow> commentRows = new ArrayList<>();
        if (comments != null) {
            for (GetUserCommentsOutputData.UserCommentData comment
                    : comments) {
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
     * Converts one review summary into one displayed row.
     * @param review the review to convert
     * @return the displayed review row
     */
    private UserReviewRow createReviewRow(
            final GetUserReviewsOutputData.UserReviewData review) {
        return new UserReviewRow(review.getReviewId(), review.getMediaId(),
                review.getMediaType(), review.getMediaTitle(),
                review.getReleaseYear(), review.getPosterPath(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount());
    }

    /**
     * Converts one comment summary into one displayed comment row.
     * @param comment the comment summary to convert
     * @return the displayed comment row
     */
    private UserCommentRow createCommentRow(
            final GetUserCommentsOutputData.UserCommentData comment) {
        return new UserCommentRow(comment.getCommentId(),
                comment.getReviewId(), comment.getMediaTitle(),
                comment.getReleaseYear(), comment.getPosterPath(),
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

}
