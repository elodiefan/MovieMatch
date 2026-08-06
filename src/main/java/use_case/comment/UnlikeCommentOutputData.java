package use_case.comment;

/**
 * Output data for unliking a comment.
 */
public class UnlikeCommentOutputData {
    private final boolean unliked;

    public UnlikeCommentOutputData(final boolean unliked) {
        this.unliked = unliked;
    }

    public boolean isUnliked() {
        return unliked;
    }
}
