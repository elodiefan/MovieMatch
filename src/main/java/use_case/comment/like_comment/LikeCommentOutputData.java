package use_case.comment.like_comment;

/** Output data for liking a comment. */
public final class LikeCommentOutputData {
    /** The liked. */
    private final boolean liked;

    /** Handles this review or comment operation. */
    public LikeCommentOutputData(final boolean inputLiked) {
        this.liked = inputLiked;
    }

    /** Handles this review or comment operation. */
    public boolean isLiked() {
        return liked;
    }
}
