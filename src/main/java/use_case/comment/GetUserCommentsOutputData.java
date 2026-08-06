package use_case.comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Output data for loading comments written by one user.
 */
public final class GetUserCommentsOutputData {
    /** The comments. */
    private final List<UserCommentSummaryData> comments;

    /**
     * Creates output data for loaded user comments.
     * @param inputComments the loaded comment summaries
     */
    public GetUserCommentsOutputData(
            final List<UserCommentSummaryData> inputComments) {
        this.comments = new ArrayList<>(inputComments);
    }

    /**
     * Returns loaded user comment summaries.
     * @return a copy of the comment summaries
     */
    public List<UserCommentSummaryData> getComments() {
        return new ArrayList<>(comments);
    }
}
