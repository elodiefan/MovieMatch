package use_case.comment;

/**
 * Output data for liking a comment.
 */
public class LikeCommentOutputData {
    private final boolean liked;

    public LikeCommentOutputData(final boolean liked) {
        this.liked = liked;
    }

    public boolean isLiked() {
        return liked;
    }
}
