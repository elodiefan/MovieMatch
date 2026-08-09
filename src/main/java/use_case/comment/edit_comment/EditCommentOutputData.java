package use_case.comment.edit_comment;

/**
 * Output data for editing a comment.
 */
public final class EditCommentOutputData {
    /**
     * Whether the comment was edited.
     */
    private final boolean edited;

    /**
     * Creates output data for editing a comment.
     * @param inputEdited whether the comment was edited
     */
    public EditCommentOutputData(final boolean inputEdited) {
        this.edited = inputEdited;
    }

    /**
     * Returns whether the comment was edited.
     * @return true if the comment was edited
     */
    public boolean isEdited() {
        return edited;
    }
}
