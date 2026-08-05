package interface_adapter.comments;

import interface_adapter.ViewModel;

/**
 * View model for review comments.
 */
public class CommentsViewModel extends ViewModel<CommentsState> {

    public static final String VIEW_NAME = "comments";
    public static final String TITLE_LABEL = "Comments";
    public static final String EMPTY_COMMENTS_MESSAGE = "No comments yet.";
    public static final String WRITE_COMMENT_BUTTON_LABEL = "Write Comment";
    public static final String REPLY_BUTTON_LABEL = "Reply";
    public static final String DELETE_BUTTON_LABEL = "Delete";
    public static final String LIKE_BUTTON_LABEL = "Like";
    public static final String UNLIKE_BUTTON_LABEL = "Unlike";

    public CommentsViewModel() {
        super(VIEW_NAME);
        setState(new CommentsState());
    }
}
