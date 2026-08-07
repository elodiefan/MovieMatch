package interface_adapter.user_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.edit_review.EditReviewOutputData;
import use_case.review.get_user_reviews.GetUserReviewsOutputData;

/**
 * Tests for the user reviews presenter.
 */
public class UserReviewsPresenterTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void prepareSuccessViewConvertsReviewsIntoRows() {
        final UserReviewsViewModel viewModel = new UserReviewsViewModel();
        final UserReviewsPresenter presenter = new UserReviewsPresenter(
                viewModel);
        final Review review = makeReview();
        review.like("lily");

        presenter.prepareSuccessView(
                new GetUserReviewsOutputData(Arrays.asList(review)));

        final List<UserReviewRow> rows = viewModel.getState().getReviews();
        assertEquals(1, rows.size());
        assertEquals("review-1", rows.get(0).getReviewId());
        assertEquals(550, rows.get(0).getMediaId());
        assertEquals("movie", rows.get(0).getMediaType());
        assertEquals("Fight Club", rows.get(0).getMediaTitle());
        assertEquals(92.0, rows.get(0).getRating());
        assertEquals("Great.", rows.get(0).getReviewText());
        assertEquals(TIME, rows.get(0).getCreatedAt());
        assertEquals(TIME.plusHours(1), rows.get(0).getUpdatedAt());
        assertEquals(1, rows.get(0).getLikeCount());
        assertNull(viewModel.getState().getUserReviewsError());
    }

    @Test
    void prepareFailViewStoresTrimmedError() {
        final UserReviewsViewModel viewModel = new UserReviewsViewModel();
        final UserReviewsPresenter presenter = new UserReviewsPresenter(
                viewModel);

        final String error = presenter.prepareFailView("  Load failed.  ");

        assertEquals("Load failed.", error);
        assertEquals("Load failed.", viewModel.getState()
                .getUserReviewsError());
    }

    @Test
    void prepareFailViewUsesDefaultErrorForBlankMessage() {
        final UserReviewsViewModel viewModel = new UserReviewsViewModel();
        final UserReviewsPresenter presenter = new UserReviewsPresenter(
                viewModel);

        final String error = presenter.prepareFailView(" ");

        assertEquals("Unable to load reviews.", error);
        assertEquals("Unable to load reviews.", viewModel.getState()
                .getUserReviewsError());
    }

    @Test
    void reviewActionSuccessClearsExistingError() {
        final UserReviewsViewModel viewModel = new UserReviewsViewModel();
        final UserReviewsPresenter presenter = new UserReviewsPresenter(
                viewModel);
        presenter.prepareFailView("Problem.");

        presenter.prepareSuccessView(new EditReviewOutputData(makeReview()));

        assertNull(viewModel.getState().getUserReviewsError());
    }

    private Review makeReview() {
        return new Review("review-1", 550, "movie", "Fight Club",
                "elodie", "Elodie", 92.0, "Great.", TIME,
                TIME.plusHours(1), "moviematch", new HashSet<>());
    }
}
