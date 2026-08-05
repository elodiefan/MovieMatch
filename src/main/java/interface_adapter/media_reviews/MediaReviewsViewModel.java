package interface_adapter.media_reviews;

import interface_adapter.ViewModel;

/**
 * View model for the media reviews panel.
 */
public class MediaReviewsViewModel extends ViewModel<MediaReviewsState> {

    public static final String VIEW_NAME = "media reviews";
    public static final String TITLE_LABEL = "Community Reviews";
    public static final String EMPTY_REVIEWS_MESSAGE = "No reviews yet.";
    public static final String WRITE_REVIEW_BUTTON_LABEL = "Write Review";
    public static final String EDIT_BUTTON_LABEL = "Edit";
    public static final String DELETE_BUTTON_LABEL = "Delete";
    public static final String LIKE_BUTTON_LABEL = "Like";
    public static final String UNLIKE_BUTTON_LABEL = "Unlike";

    public MediaReviewsViewModel() {
        super(VIEW_NAME);
        setState(new MediaReviewsState());
    }
}
