package interface_adapter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import interface_adapter.comments.CommentRow;
import interface_adapter.comments.CommentsPresenter;
import interface_adapter.comments.CommentsViewModel;
import interface_adapter.get_lists.GetListRow;
import interface_adapter.get_lists.GetListsPresenter;
import interface_adapter.get_lists.GetListsViewModel;
import interface_adapter.media_reviews.MediaReviewRow;
import interface_adapter.media_reviews.MediaReviewsPresenter;
import interface_adapter.media_reviews.MediaReviewsViewModel;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountViewModel;
import interface_adapter.reset_password.ResetPasswordViewModel;
import interface_adapter.security_question.SecurityQuestionPresenter;
import interface_adapter.security_question.SecurityQuestionViewModel;
import interface_adapter.user_reviews.UserCommentRow;
import interface_adapter.user_reviews.UserReviewRow;
import interface_adapter.user_reviews.UserReviewsPresenter;
import interface_adapter.user_reviews.UserReviewsViewModel;
import org.junit.jupiter.api.Test;
import use_case.comment.get_review_comments.GetReviewCommentsOutputData;
import use_case.comment.get_user_comments.GetUserCommentsOutputData;
import use_case.get_lists.get_watch_history.GetWatchHistoryOutputData;
import use_case.get_lists.get_watch_history.WatchHistoryItemData;
import use_case.get_lists.get_watchlist.GetWatchlistOutputData;
import use_case.get_lists.get_watchlist.WatchlistItemData;
import use_case.review.get_media_reviews.GetMediaReviewsOutputData;
import use_case.review.get_user_reviews.GetUserReviewsOutputData;
import use_case.security_question.SecurityQuestionOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests non-empty presenter conversions and failure branches. */
class PresenterNonEmptyAndFailureTest {

    private static final ZonedDateTime CREATED_AT =
            ZonedDateTime.parse("2025-01-02T03:04:05Z");
    private static final ZonedDateTime UPDATED_AT =
            ZonedDateTime.parse("2025-01-03T03:04:05Z");

    @Test
    void getListsPresenterConvertsNonEmptyWatchlistAndHistory() {
        final GetListsViewModel viewModel = new GetListsViewModel();
        final GetListsPresenter presenter = new GetListsPresenter(
                new ViewManagerModel(), viewModel,
                new PersonalAccountViewModel(), new OtherAccountViewModel());

        presenter.prepareSuccessView(new GetWatchlistOutputData(
                "alice", "Alice", "watchlist",
                List.of(new WatchlistItemData(
                        11, "movie", "Arrival", "2025-01-01", "/a.jpg"))));

        GetListRow row = viewModel.getState().getListRows().get(0);
        assertEquals(11, row.getMediaId());
        assertEquals("movie", row.getMediaType());
        assertEquals("Arrival", row.getMediaTitle());
        assertEquals("2025-01-01", row.getLoggedAt());
        assertEquals("/a.jpg", row.getPosterPath());

        presenter.prepareSuccessView(new GetWatchHistoryOutputData(
                "alice", "Alice", "history",
                List.of(new WatchHistoryItemData(
                        22, "tv", "Severance", "2025-02-02", "/s.jpg"))));

        row = viewModel.getState().getListRows().get(0);
        assertEquals(22, row.getMediaId());
        assertEquals("tv", row.getMediaType());
        assertEquals("Severance", row.getMediaTitle());
        assertEquals("2025-02-02", row.getLoggedAt());
        assertEquals("/s.jpg", row.getPosterPath());
    }

    @Test
    void commentsPresenterConvertsNonEmptyResultAndHandlesFailures() {
        final CommentsViewModel viewModel = new CommentsViewModel();
        final CommentsPresenter presenter = new CommentsPresenter(viewModel);
        viewModel.getState().setComments(List.of(new CommentRow(
                "old-comment", "review-1", null, "old-author", "Old",
                "Old text", CREATED_AT, 0, Set.of())));
        final GetReviewCommentsOutputData.ReviewCommentData comment =
                new GetReviewCommentsOutputData.ReviewCommentData(
                        "comment-1", "review-1", "parent-1", "alice",
                        "Alice", "Great review", CREATED_AT, 4,
                        Set.of("bob"));

        presenter.prepareSuccessView(new GetReviewCommentsOutputData(
                "review-1", List.of(comment)));

        final CommentRow row = viewModel.getState().getComments().get(0);
        assertEquals(1, viewModel.getState().getComments().size());
        assertEquals("comment-1", row.getCommentId());
        assertEquals("review-1", row.getReviewId());
        assertEquals("parent-1", row.getParentCommentId());
        assertEquals("alice", row.getAuthorUsername());
        assertEquals("Alice", row.getAuthorDisplayName());
        assertEquals("Great review", row.getCommentText());
        assertEquals(CREATED_AT, row.getCreatedAt());
        assertEquals(4, row.getLikeCount());
        assertTrue(row.isLikedBy("bob"));
        assertFalse(row.isLikedBy(null));
        assertNull(viewModel.getState().getCommentsError());

        assertEquals("Unable to load comments.", presenter.prepareFailView(null));
        assertEquals("Unable to load comments.", presenter.prepareFailView("  "));
        assertEquals("network error", presenter.prepareFailView(" network error "));
        assertEquals("network error", viewModel.getState().getCommentsError());
        assertEquals("Unable to load comments.",
                new CommentsPresenter().prepareFailView(null));
    }

    @Test
    void userReviewsPresenterConvertsNonEmptyReviewsAndComments() {
        final UserReviewsViewModel viewModel = new UserReviewsViewModel();
        final UserReviewsPresenter presenter = new UserReviewsPresenter(viewModel);
        final GetUserReviewsOutputData.UserReviewData review =
                new GetUserReviewsOutputData.UserReviewData(
                        "review-1", 11, "movie", "Arrival", 2016,
                        "/a.jpg", 8.5, "Excellent", CREATED_AT,
                        UPDATED_AT, 7);
        final GetUserCommentsOutputData.UserCommentData comment =
                new GetUserCommentsOutputData.UserCommentData(
                        "comment-1", "review-1", 11, "movie", "Arrival",
                        2016, "/a.jpg", "Excellent", "Agreed",
                        CREATED_AT, 3);

        presenter.prepareUserReviewsSuccessView(
                new GetUserReviewsOutputData(List.of(review)));
        presenter.prepareUserCommentsSuccessView(
                new GetUserCommentsOutputData(List.of(comment)));

        final UserReviewRow reviewRow =
                viewModel.getState().getReviews().get(0);
        assertEquals("review-1", reviewRow.getReviewId());
        assertEquals(11, reviewRow.getMediaId());
        assertEquals("Arrival", reviewRow.getMediaTitle());
        assertEquals(8.5, reviewRow.getRating());
        assertEquals(UPDATED_AT, reviewRow.getUpdatedAt());
        assertEquals(7, reviewRow.getLikeCount());

        final UserCommentRow commentRow =
                viewModel.getState().getComments().get(0);
        assertEquals("comment-1", commentRow.getCommentId());
        assertEquals("review-1", commentRow.getReviewId());
        assertEquals("Agreed", commentRow.getCommentText());
        assertEquals(CREATED_AT, commentRow.getCreatedAt());
        assertEquals(3, commentRow.getLikeCount());

        assertEquals("Unable to load reviews.", presenter.prepareFailView(null));
        assertEquals("Unable to load reviews.", presenter.prepareFailView(" "));
        assertEquals("database error", presenter.prepareFailView(" database error "));
        assertEquals("database error", viewModel.getState().getUserReviewsError());
    }

    @Test
    void mediaReviewsPresenterConvertsNonEmptyResultAndHandlesFailures() {
        final MediaReviewsViewModel viewModel = new MediaReviewsViewModel();
        final MediaReviewsPresenter presenter = new MediaReviewsPresenter(viewModel);
        final GetMediaReviewsOutputData.MediaReviewData review =
                new GetMediaReviewsOutputData.MediaReviewData(
                        "review-1", "alice", "Alice", 9.0, "Loved it",
                        CREATED_AT, UPDATED_AT, 5, Set.of("bob"), "local");

        presenter.prepareSuccessView(
                new GetMediaReviewsOutputData(List.of(review)));

        final MediaReviewRow row = viewModel.getState().getReviews().get(0);
        assertEquals("review-1", row.getReviewId());
        assertEquals("alice", row.getAuthorUsername());
        assertEquals("Alice", row.getAuthorDisplayName());
        assertEquals(9.0, row.getRating());
        assertEquals("Loved it", row.getReviewText());
        assertEquals(CREATED_AT, row.getCreatedAt());
        assertEquals(UPDATED_AT, row.getUpdatedAt());
        assertEquals(5, row.getLikeCount());
        assertTrue(row.isLikedBy("bob"));
        assertFalse(row.isLikedBy(null));
        assertEquals("local", row.getSource());
        assertTrue(presenter.prepareReviews(null).isEmpty());

        assertEquals("Unable to load media reviews.",
                presenter.prepareFailView(null));
        assertEquals("Unable to load media reviews.",
                presenter.prepareFailView(" "));
        assertEquals("service error", presenter.prepareFailView(" service error "));
        assertEquals("service error",
                viewModel.getState().getMediaReviewsError());
        assertEquals("Unable to load media reviews.",
                new MediaReviewsPresenter().prepareFailView(null));
    }

    @Test
    void securityQuestionPresenterCoversEveryFailureMessage() {
        final SecurityQuestionViewModel viewModel =
                new SecurityQuestionViewModel();
        final SecurityQuestionPresenter presenter =
                new SecurityQuestionPresenter(viewModel,
                        new ResetPasswordViewModel(), () -> { }, () -> { });

        presenter.prepareFailView(new SecurityQuestionOutputData(
                "alice", "Question?", true, 0, true, 61));
        assertEquals("Too many incorrect attempts. Account locked for about "
                        + "2 minute(s). Please try again later.",
                viewModel.getState().getError());
        assertTrue(viewModel.getState().isLockedOut());

        presenter.prepareFailView(new SecurityQuestionOutputData(
                "missing", "", true, 0, false, 0));
        assertEquals("No account found with that username.",
                viewModel.getState().getError());
        assertFalse(viewModel.getState().isLockedOut());

        presenter.prepareFailView(new SecurityQuestionOutputData(
                "alice", "Question?", true, 2, false, 0));
        assertEquals("Incorrect answer. Attempts remaining: 2.",
                viewModel.getState().getError());
    }
}
