package interface_adapter.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import use_case.recommendation.GenreSection;
import use_case.recommendation.RecommendationOutputData;
import use_case.recommendation.RecommendedMedia;

/**
 * Tests turning results into what the recommendation screens draw.
 */
class RecommendationPresenterTest {

    private static final String USER = "enzo";

    /**
     * Runs the update immediately and counts how often it was asked to.
     *
     * The real one hands the work to Swing. Using a plain Executor is what
     * keeps the presenter free of any UI framework, and lets this run without one.
     */
    private static final class ImmediateExecutor implements Executor {
        private int handled;

        @Override
        public void execute(Runnable command) {
            handled++;
            command.run();
        }
    }

    private RecommendationViewModel viewModel;
    private ImmediateExecutor executor;
    private RecommendationPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new RecommendationViewModel();
        executor = new ImmediateExecutor();
        presenter = new RecommendationPresenter(viewModel, executor);
    }

    private static RecommendedMedia media(int id, String title, String posterPath) {
        return new RecommendedMedia(id, title, 2024, 0.8, "Drama", "a reason", posterPath);
    }

    private static RecommendationOutputData results() {
        return new RecommendationOutputData(USER,
                List.of(media(1, "First", "/one.jpg"), media(2, "Second", "/two.jpg")),
                List.of(new GenreSection("Drama", List.of(media(3, "Third", "/three.jpg")))));
    }

    @Test
    @DisplayName("results become rows the screen can draw")
    void resultsBecomeRows() {
        presenter.presentRecommendations(results());

        final List<RecommendationRow> rows = viewModel.getState().getRecommendations();
        assertEquals(2, rows.size());
        assertEquals("First", rows.get(0).getTitle());
        assertEquals(2, rows.get(1).getMediaId());
        assertEquals(2024, rows.get(0).getReleaseYear());
        assertEquals(0.8, rows.get(0).getScore(), 0.0001);
        assertEquals("Drama", rows.get(0).getPrimaryGenre());
        assertEquals("a reason", rows.get(0).getExplanation());
    }

    @Test
    @DisplayName("the poster path reaches the row")
    void posterPathReachesTheRow() {
        presenter.presentRecommendations(results());

        assertEquals("/one.jpg",
                viewModel.getState().getRecommendations().get(0).getPosterPath());
    }

    @Test
    @DisplayName("sections keep their heading and their rows' posters")
    void sectionsAreBuilt() {
        presenter.presentRecommendations(results());

        final List<RecommendationSection> sections = viewModel.getState().getSections();
        assertEquals(1, sections.size());
        assertTrue(sections.get(0).getHeading().contains("Drama"));
        assertEquals(1, sections.get(0).getRecommendations().size());
        assertEquals("/three.jpg",
                sections.get(0).getRecommendations().get(0).getPosterPath());
    }

    @Test
    @DisplayName("a successful load clears any earlier error")
    void successClearsTheError() {
        presenter.prepareFailView("it broke");
        presenter.presentRecommendations(results());

        assertNull(viewModel.getState().getRecommendationError());
        assertTrue(viewModel.getState().isLoaded());
    }

    @Test
    @DisplayName("a failure is reported and the screen stops waiting")
    void failureIsReported() {
        presenter.prepareFailView("Unable to load recommendations from TMDB.");

        assertEquals("Unable to load recommendations from TMDB.",
                viewModel.getState().getRecommendationError());
        assertTrue(viewModel.getState().isLoaded(),
                "the screen has to stop showing a spinner once it has failed");
    }

    @Test
    @DisplayName("the username comes back so the full list can be opened for them")
    void usernameIsKept() {
        presenter.presentRecommendations(results());

        assertEquals(USER, viewModel.getState().getUsername());
    }

    @Test
    @DisplayName("every update goes through the executor, never straight to the model")
    void updatesGoThroughTheExecutor() {
        // Results arrive on a background thread, so the screen must only be
        // touched through the executor the view layer supplied.
        presenter.presentRecommendations(results());
        presenter.prepareFailView("it broke");

        assertEquals(2, executor.handled);
    }

    @Test
    @DisplayName("no results is a valid answer, not a failure")
    void emptyResultsAreNotAnError() {
        presenter.presentRecommendations(new RecommendationOutputData(
                USER, new ArrayList<>(), new ArrayList<>()));

        assertTrue(viewModel.getState().getRecommendations().isEmpty());
        assertNull(viewModel.getState().getRecommendationError());
        assertTrue(viewModel.getState().isLoaded());
    }

    @Test
    @DisplayName("the screen is told each time something changed")
    void listenersAreNotified() {
        final boolean[] told = {false};
        viewModel.addPropertyChangeListener(event -> told[0] = true);

        presenter.presentRecommendations(results());

        assertTrue(told[0]);
    }

    @Test
    @DisplayName("nothing is loaded before the first result arrives")
    void notLoadedToBeginWith() {
        assertFalse(new RecommendationViewModel().getState().isLoaded(),
                "an empty list before loading must not read as no suggestions");
    }
}
