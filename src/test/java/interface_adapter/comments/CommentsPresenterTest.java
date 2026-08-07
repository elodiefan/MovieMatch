package interface_adapter.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import entity.Comment;
import org.junit.jupiter.api.Test;
import use_case.comment.create_comment.CreateCommentOutputData;
import use_case.comment.get_review_comments.GetReviewCommentsOutputData;

/**
 * Tests for the comments presenter.
 */
public class CommentsPresenterTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void prepareSuccessViewConvertsCommentsIntoRows() {
        final CommentsViewModel viewModel = new CommentsViewModel();
        final CommentsPresenter presenter = new CommentsPresenter(viewModel);
        final Comment comment = makeComment("comment-1", "review-1", null);
        comment.like("lily");

        presenter.prepareSuccessView(new GetReviewCommentsOutputData(
                "review-1", Arrays.asList(comment)));

        final List<CommentRow> rows = viewModel.getState().getComments();
        assertEquals(1, rows.size());
        assertEquals("comment-1", rows.get(0).getCommentId());
        assertEquals("review-1", rows.get(0).getReviewId());
        assertNull(rows.get(0).getParentCommentId());
        assertEquals("elodie", rows.get(0).getAuthorUsername());
        assertEquals("Elodie", rows.get(0).getAuthorDisplayName());
        assertEquals("I agree.", rows.get(0).getCommentText());
        assertEquals(TIME, rows.get(0).getCreatedAt());
        assertEquals(1, rows.get(0).getLikeCount());
        assertNull(viewModel.getState().getCommentsError());
    }

    @Test
    void prepareSuccessViewReplacesRowsForSameReviewOnly() {
        final CommentsViewModel viewModel = new CommentsViewModel();
        final CommentsState state = viewModel.getState();
        state.setComments(Arrays.asList(
                new CommentRow("old-1", "review-1", null, "elodie",
                        "Elodie", "Old.", TIME, 0),
                new CommentRow("old-2", "review-2", null, "lily",
                        "Lily", "Keep.", TIME, 0)));
        viewModel.setState(state);
        final CommentsPresenter presenter = new CommentsPresenter(viewModel);

        presenter.prepareSuccessView(new GetReviewCommentsOutputData(
                "review-1", Arrays.asList(makeComment("new-1", "review-1",
                null))));

        final List<CommentRow> rows = viewModel.getState().getComments();
        assertEquals(2, rows.size());
        assertEquals("old-2", rows.get(0).getCommentId());
        assertEquals("new-1", rows.get(1).getCommentId());
    }

    @Test
    void prepareFailViewStoresTrimmedError() {
        final CommentsViewModel viewModel = new CommentsViewModel();
        final CommentsPresenter presenter = new CommentsPresenter(viewModel);

        final String error = presenter.prepareFailView("  Load failed.  ");

        assertEquals("Load failed.", error);
        assertEquals("Load failed.", viewModel.getState()
                .getCommentsError());
    }

    @Test
    void prepareFailViewUsesDefaultErrorForBlankMessage() {
        final CommentsViewModel viewModel = new CommentsViewModel();
        final CommentsPresenter presenter = new CommentsPresenter(viewModel);

        final String error = presenter.prepareFailView(" ");

        assertEquals("Unable to load comments.", error);
        assertEquals("Unable to load comments.", viewModel.getState()
                .getCommentsError());
    }

    @Test
    void commentActionSuccessClearsExistingError() {
        final CommentsViewModel viewModel = new CommentsViewModel();
        final CommentsPresenter presenter = new CommentsPresenter(viewModel);
        presenter.prepareFailView("Problem.");

        presenter.prepareSuccessView(new CreateCommentOutputData(
                makeComment("comment-1", "review-1", null)));

        assertNull(viewModel.getState().getCommentsError());
    }

    private Comment makeComment(String commentId, String reviewId,
                                String parentCommentId) {
        return new Comment(commentId, reviewId, parentCommentId, "elodie",
                "Elodie", "I agree.", TIME, new HashSet<>());
    }
}
