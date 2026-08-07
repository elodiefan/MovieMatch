package interface_adapter.account;

import interface_adapter.ViewModel;

/** The View Model for the reviews view opened from a user's account. */
public class ReviewsViewModel extends ViewModel<ReviewsState> {

    public static final String TITLE_LABEL = "My Reviews and Comments";
    public static final String REVIEWS_TAB_LABEL = "Reviews";
    public static final String COMMENTS_TAB_LABEL = "Comments";
    public static final String BACK_BUTTON_LABEL = "Back";

    public ReviewsViewModel() {
        super("reviews");
        setState(new ReviewsState());
    }
}
