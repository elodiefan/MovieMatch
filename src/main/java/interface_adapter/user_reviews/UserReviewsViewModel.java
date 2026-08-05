package interface_adapter.user_reviews;

import interface_adapter.ViewModel;

/**
 * View model for the user reviews view.
 */
public class UserReviewsViewModel extends ViewModel<UserReviewsState> {

    public static final String VIEW_NAME = "my reviews";
    public static final String TITLE_LABEL = "My Reviews";
    public static final String EMPTY_REVIEWS_MESSAGE = "No reviews yet.";
    public static final String EDIT_BUTTON_LABEL = "Edit";
    public static final String DELETE_BUTTON_LABEL = "Delete";
    public static final String LIKE_BUTTON_LABEL = "Like";
    public static final String UNLIKE_BUTTON_LABEL = "Unlike";
    public static final String BACK_BUTTON_LABEL = "Back";

    public UserReviewsViewModel() {
        super(VIEW_NAME);
        setState(new UserReviewsState());
    }
}
