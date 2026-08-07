package use_case.comment.get_user_comments;

import use_case.comment.UserCommentSummaryData;

import java.util.ArrayList;
import java.util.List;

/** Output data for loading comments written by one user. */
public final class GetUserCommentsOutputData {
    /** The comments. */
    private final List<UserCommentSummaryData> comments;

    /** Creates output data for loaded user comments. */
    public GetUserCommentsOutputData(
            final List<UserCommentSummaryData> inputComments) {
        this.comments = new ArrayList<>(inputComments);
    }

    /** Returns loaded user comment summaries. */
    public List<UserCommentSummaryData> getComments() {
        return new ArrayList<>(comments);
    }
}
