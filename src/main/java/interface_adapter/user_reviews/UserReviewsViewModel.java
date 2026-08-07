package interface_adapter.user_reviews;

import interface_adapter.ViewModel;

/**
 * View model for the user reviews view.
 */
public final class UserReviewsViewModel extends ViewModel<UserReviewsState> {

    /** The view name. */
    public static final String VIEW_NAME = "my reviews";
    /** The title label. */
    public static final String TITLE_LABEL = "My Reviews";
    /** The reviews tab label. */
    public static final String REVIEWS_TAB_LABEL = "Reviews";
    /** The comments tab label. */
    public static final String COMMENTS_TAB_LABEL = "Comments";
    /** The empty reviews message. */
    public static final String EMPTY_REVIEWS_MESSAGE = "No reviews yet.";
    /** The empty comments message. */
    public static final String EMPTY_COMMENTS_MESSAGE = "No comments yet.";
    /** The edit button label. */
    public static final String EDIT_BUTTON_LABEL = "Edit";
    /** The delete button label. */
    public static final String DELETE_BUTTON_LABEL = "Delete";
    /** The like button label. */
    public static final String LIKE_BUTTON_LABEL = "Like";
    /** The unlike button label. */
    public static final String UNLIKE_BUTTON_LABEL = "Unlike";
    /** The back button label. */
    public static final String BACK_BUTTON_LABEL = "Back";

    /**
     * Handles this review or comment operation.
     */
    public UserReviewsViewModel() {
        super(VIEW_NAME);
        setState(new UserReviewsState());
    }
}
