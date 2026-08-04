package interface_adapter.user_reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the user reviews view.
 */
public class UserReviewsState {
    private String username = "";
    private List<UserReviewsPresenter.UserReviewRow> reviews = new ArrayList<>();
    private String selectedReviewId = "";
    private String userReviewsError;

    /**
     * Returns the username whose reviews are displayed.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username whose reviews are displayed.
     * @param username the username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns the review rows displayed in the view.
     * @return a copy of the review rows
     */
    public List<UserReviewsPresenter.UserReviewRow> getReviews() {
        return new ArrayList<>(reviews);
    }

    /**
     * Sets the review rows displayed in the view.
     * @param reviews the review rows
     */
    public void setReviews(
            final List<UserReviewsPresenter.UserReviewRow> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }

    /**
     * Returns the selected review id.
     * @return the selected review id
     */
    public String getSelectedReviewId() {
        return selectedReviewId;
    }

    /**
     * Sets the selected review id.
     * @param selectedReviewId the selected review id
     */
    public void setSelectedReviewId(final String selectedReviewId) {
        this.selectedReviewId = selectedReviewId;
    }

    /**
     * Returns the current user reviews error message.
     * @return the error message
     */
    public String getUserReviewsError() {
        return userReviewsError;
    }

    /**
     * Sets the current user reviews error message.
     * @param userReviewsError the error message
     */
    public void setUserReviewsError(final String userReviewsError) {
        this.userReviewsError = userReviewsError;
    }
}
