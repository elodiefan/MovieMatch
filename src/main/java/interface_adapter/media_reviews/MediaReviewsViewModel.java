package interface_adapter.media_reviews;

import interface_adapter.ViewModel;

/**
 * View model for the media reviews panel.
 */
public final class MediaReviewsViewModel extends ViewModel<MediaReviewsState> {

    /**
     * The view name.
     */
    public static final String VIEW_NAME = "media reviews";
    /**
     * The title label.
     */
    public static final String TITLE_LABEL = "Community Reviews";
    /**
     * The empty reviews message.
     */
    public static final String EMPTY_REVIEWS_MESSAGE = "No reviews yet.";
    /**
     * The write review button label.
     */
    public static final String WRITE_REVIEW_BUTTON_LABEL = "Write Review";
    /**
     * The edit button label.
     */
    public static final String EDIT_BUTTON_LABEL = "Edit";
    /**
     * The delete button label.
     */
    public static final String DELETE_BUTTON_LABEL = "Delete";
    /**
     * The like button label.
     */
    public static final String LIKE_BUTTON_LABEL = "Like";
    /**
     * The unlike button label.
     */
    public static final String UNLIKE_BUTTON_LABEL = "Unlike";

    /**
     * Handles this review or comment operation.
     */
    public MediaReviewsViewModel() {
        super(VIEW_NAME);
        setState(new MediaReviewsState());
    }
}
