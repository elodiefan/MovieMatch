package use_case.review.create_review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import entity.Review;

/**
 * Tests for the create review interactor.
 */
class CreateReviewInteractorTest {

    @Test
    void successSavesReviewWhenMediaIsInWatchHistory() {
        final RecordingReviewDao dao = new RecordingReviewDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                dao, (username, mediaId, mediaType) -> true, presenter);

        interactor.execute(new CreateReviewInputData(42, " movie ", " Example Movie ", 2025,
                "/poster.jpg", " bob ", " Bob ", 85.0, " Enjoyed it "));

        assertNotNull(dao.savedReview);
        assertEquals("bob", dao.savedReview.getAuthorUsername());
        assertEquals(85.0, dao.savedReview.getRating());
        assertNotNull(presenter.success);
        assertNull(presenter.failure);
    }

    @Test
    void unwatchedMediaIsReportedAsFailure() {
        final RecordingReviewDao dao = new RecordingReviewDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                dao, (username, mediaId, mediaType) -> false, presenter);

        interactor.execute(new CreateReviewInputData(42, "movie", "Example Movie", 2025,
                "/poster.jpg", "bob", "Bob", 85.0, "Enjoyed it"));

        assertNull(dao.savedReview);
        assertNull(presenter.success);
        assertEquals("Please add this media to your watch history before writing a review.",
                presenter.failure);
    }

    @Test
    void negativeMediaIdIsReportedAsFailure() {
        assertFailure(-1, "movie", "Title", "bob", "Bob", 80.0,
                "Media id cannot be negative.");
    }

    @Test
    void blankMediaTypeIsReportedAsFailure() {
        assertFailure(1, " ", "Title", "bob", "Bob", 80.0,
                "Media type cannot be empty.");
    }

    @Test
    void blankMediaTitleIsReportedAsFailure() {
        assertFailure(1, "movie", " ", "bob", "Bob", 80.0,
                "Media title cannot be empty.");
    }

    @Test
    void blankUsernameIsReportedAsFailure() {
        assertFailure(1, "movie", "Title", " ", "Bob", 80.0,
                "Author username cannot be empty.");
    }

    @Test
    void blankDisplayNameIsReportedAsFailure() {
        assertFailure(1, "movie", "Title", "bob", " ", 80.0,
                "Author display name cannot be empty.");
    }

    @Test
    void ratingAboveOneHundredIsReportedAsFailure() {
        assertFailure(1, "movie", "Title", "bob", "Bob", 101.0,
                "Rating must be between 0 and 100.");
    }

    @Test
    void canCreateReviewReturnsTrueForWatchedMedia() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                new RecordingReviewDao(), (username, id, type) -> true, presenter);

        assertEquals(true, interactor.canCreateReview(new CreateReviewInputData(1, " movie ", " bob ")));
        assertNull(presenter.failure);
    }

    @Test
    void missingReviewDataAccessIsReportedAsFailure() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                null, (username, id, type) -> true, presenter);

        interactor.execute(new CreateReviewInputData(1, "movie", "Title", 2025, "", "bob", "Bob",
                80.0, "Text"));

        assertEquals("Review data access object has not been configured.",
                presenter.failure);
    }

    @Test
    void missingUserDataAccessPreventsReviewPermission() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                new RecordingReviewDao(), null, presenter);

        assertEquals(false, interactor.canCreateReview(1, "movie", "bob"));
        assertEquals("User data access object has not been configured.",
                presenter.failure);
    }

    private static void assertFailure(final int mediaId, final String mediaType,
                                      final String title, final String username,
                                      final String displayName, final double rating,
                                      final String expectedMessage) {
        final RecordingPresenter presenter = new RecordingPresenter();
        final CreateReviewInteractor interactor = new CreateReviewInteractor(
                new RecordingReviewDao(), (user, id, type) -> true, presenter);

        interactor.execute(new CreateReviewInputData(mediaId, mediaType, title, 2025, "", username,
                displayName, rating, "Text"));

        assertEquals(expectedMessage, presenter.failure);
    }

    private static final class RecordingReviewDao implements CreateReviewDataAccessInterface {
        private Review savedReview;

        @Override
        public void saveReview(final Review review) {
            savedReview = review;
        }
    }

    private static final class RecordingPresenter implements CreateReviewOutputBoundary {
        private CreateReviewOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(final CreateReviewOutputData review) {
            success = review;
        }

        @Override
        public String prepareFailView(final String errorMessage) {
            failure = errorMessage;
            return errorMessage;
        }
    }

}
