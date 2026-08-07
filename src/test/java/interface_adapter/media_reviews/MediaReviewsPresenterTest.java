package interface_adapter.media_reviews;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import entity.Review;
import org.junit.jupiter.api.Test;
import use_case.review.create_review.CreateReviewOutputData;
import use_case.review.get_media_reviews.GetMediaReviewsOutputData;

/**
 * Tests for the media reviews presenter.
 */
public class MediaReviewsPresenterTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void prepareSuccessViewConvertsReviewsIntoRows() {
        final MediaReviewsViewModel viewModel = new MediaReviewsViewModel();
        final MediaReviewsPresenter presenter =
                new MediaReviewsPresenter(viewModel);
        final Review review = makeReview();
        review.like("lily");

        presenter.prepareSuccessView(
                new GetMediaReviewsOutputData(Arrays.asList(review)));

        final List<MediaReviewRow> rows = viewModel.getState().getReviews();
        assertEquals(1, rows.size());
        assertEquals("review-1", rows.get(0).getReviewId());
        assertEquals("elodie", rows.get(0).getAuthorUsername());
        assertEquals("Elodie", rows.get(0).getAuthorDisplayName());
        assertEquals(92.0, rows.get(0).getRating());
        assertEquals("Great.", rows.get(0).getReviewText());
        assertEquals(TIME, rows.get(0).getCreatedAt());
        assertEquals(TIME.plusHours(1), rows.get(0).getUpdatedAt());
        assertEquals(1, rows.get(0).getLikeCount());
        assertEquals("moviematch", rows.get(0).getSource());
        assertNull(viewModel.getState().getMediaReviewsError());
    }

    @Test
    void prepareFailViewStoresTrimmedError() {
        final MediaReviewsViewModel viewModel = new MediaReviewsViewModel();
        final MediaReviewsPresenter presenter =
                new MediaReviewsPresenter(viewModel);

        final String error = presenter.prepareFailView("  Load failed.  ");

        assertEquals("Load failed.", error);
        assertEquals("Load failed.",
                viewModel.getState().getMediaReviewsError());
    }

    @Test
    void prepareFailViewUsesDefaultErrorForBlankMessage() {
        final MediaReviewsViewModel viewModel = new MediaReviewsViewModel();
        final MediaReviewsPresenter presenter =
                new MediaReviewsPresenter(viewModel);

        final String error = presenter.prepareFailView(" ");

        assertEquals("Unable to load media reviews.", error);
        assertEquals("Unable to load media reviews.",
                viewModel.getState().getMediaReviewsError());
    }

    @Test
    void reviewActionSuccessClearsExistingError() {
        final MediaReviewsViewModel viewModel = new MediaReviewsViewModel();
        final MediaReviewsPresenter presenter =
                new MediaReviewsPresenter(viewModel);
        presenter.prepareFailView("Problem.");

        presenter.prepareSuccessView(new CreateReviewOutputData(makeReview()));

        assertNull(viewModel.getState().getMediaReviewsError());
    }

    private Review makeReview() {
        return new Review("review-1", 550, "movie", "Fight Club",
                "elodie", "Elodie", 92.0, "Great.", TIME,
                TIME.plusHours(1), "moviematch", new HashSet<>());
    }
}
