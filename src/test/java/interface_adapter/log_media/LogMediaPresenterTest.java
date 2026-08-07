package interface_adapter.log_media;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import use_case.log_media.LogMediaOutputData;

/**
 * Tests for the log media presenter.
 */
class LogMediaPresenterTest {

    @Test
    void prepareSuccessViewStoresMessageAndClearsError() {
        final LogMediaViewModel viewModel = new LogMediaViewModel();
        final LogMediaState state = viewModel.getState();
        state.setError("Old error.");
        viewModel.setState(state);
        final LogMediaPresenter presenter = new LogMediaPresenter(viewModel);

        presenter.prepareSuccessView(new LogMediaOutputData("Fight Club",
                "Added to watchlist."));

        assertEquals("Added to watchlist.",
                viewModel.getState().getMessage());
        assertEquals("", viewModel.getState().getError());
    }

    @Test
    void prepareFailViewStoresErrorAndClearsMessage() {
        final LogMediaViewModel viewModel = new LogMediaViewModel();
        final LogMediaState state = viewModel.getState();
        state.setMessage("Old message.");
        viewModel.setState(state);
        final LogMediaPresenter presenter = new LogMediaPresenter(viewModel);

        presenter.prepareFailView("Please log in before saving media.");

        assertEquals("", viewModel.getState().getMessage());
        assertEquals("Please log in before saving media.",
                viewModel.getState().getError());
    }
}
