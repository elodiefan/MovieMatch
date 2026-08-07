package interface_adapter.comments;

import interface_adapter.ViewModel;

/** View model for review comments. */
public final class CommentsViewModel extends ViewModel<CommentsState> {

    /** The view name. */
    public static final String VIEW_NAME = "comments";
    /** The title label. */
    public static final String TITLE_LABEL = "Comments";
    /** The empty comments message. */
    public static final String EMPTY_COMMENTS_MESSAGE = "No comments yet.";
    /** The write comment button label. */
    public static final String WRITE_COMMENT_BUTTON_LABEL = "Write Comment";
    /** The reply button label. */
    public static final String REPLY_BUTTON_LABEL = "Reply";
    /** The delete button label. */
    public static final String DELETE_BUTTON_LABEL = "Delete";
    /** The like button label. */
    public static final String LIKE_BUTTON_LABEL = "Like";
    /** The unlike button label. */
    public static final String UNLIKE_BUTTON_LABEL = "Unlike";

    /** Handles this review or comment operation. */
    public CommentsViewModel() {
        super(VIEW_NAME);
        setState(new CommentsState());
    }
}
