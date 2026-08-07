package use_case.get_review_comments;

import java.util.ArrayList;
import java.util.List;

import entity.Comment;

/**
 * Converts comment entities into display-safe comment data.
 */
public final class CommentSummaryMapper {

    private CommentSummaryMapper() {
    }

    /**
     * Converts one comment entity.
     */
    public static CommentSummaryData toSummary(final Comment comment) {
        return new CommentSummaryData(comment.getCommentId(),
                comment.getReviewId(), comment.getParentCommentId(),
                comment.getAuthorUsername(), comment.getAuthorDisplayName(),
                comment.getCommentText(), comment.getCreatedAt(),
                comment.getLikeCount());
    }

    /**
     * Converts comment entities into summaries.
     */
    public static List<CommentSummaryData> toSummaries(
            final List<Comment> comments) {
        final List<CommentSummaryData> summaries = new ArrayList<>();
        if (comments != null) {
            for (Comment comment : comments) {
                if (comment != null) {
                    summaries.add(toSummary(comment));
                }
            }
        }
        return summaries;
    }
}
