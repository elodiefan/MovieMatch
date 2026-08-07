package interface_adapter.user_reviews;

import java.util.ArrayList;
import java.util.List;

/** State for the user reviews view. */
public final class UserReviewsState {
    /** The username. */
    private String username = "";
    /** The reviews. */
    private List<UserReviewRow> reviews = new ArrayList<>();
    /** The comments. */
    private List<UserCommentRow> comments = new ArrayList<>();
    /** The selected review id. */
    private String selectedReviewId = "";
    /** The user reviews error. */
    private String userReviewsError;

    /** Returns the username whose reviews are displayed. */
    public String getUsername() {
        return username;
    }

    /** Sets the username whose reviews are displayed. */
    public void setUsername(final String inputUsername) {
        this.username = inputUsername;
    }

    /** Returns the review rows displayed in the view. */
    public List<UserReviewRow> getReviews() {
        return new ArrayList<>(reviews);
    }

    /** Sets the review rows displayed in the view. */
    public void setReviews(final List<UserReviewRow> inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /** Returns the comment rows displayed in the view. */
    public List<UserCommentRow> getComments() {
        return new ArrayList<>(comments);
    }

    /** Sets the comment rows displayed in the view. */
    public void setComments(final List<UserCommentRow> inputComments) {
        this.comments = new ArrayList<>(inputComments);
    }

    /** Returns the selected review id. */
    public String getSelectedReviewId() {
        return selectedReviewId;
    }

    /** Sets the selected review id. */
    public void setSelectedReviewId(final String inputSelectedReviewId) {
        this.selectedReviewId = inputSelectedReviewId;
    }

    /** Returns the current user reviews error message. */
    public String getUserReviewsError() {
        return userReviewsError;
    }

    /** Sets the current user reviews error message. */
    public void setUserReviewsError(final String inputUserReviewsError) {
        this.userReviewsError = inputUserReviewsError;
    }
}
