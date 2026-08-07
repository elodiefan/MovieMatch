package interface_adapter.log_media;

import use_case.log_media.LogMediaOutputBoundary;
import use_case.log_media.LogMediaOutputData;

/** Presenter for logging media to user lists. */
public class LogMediaPresenter implements LogMediaOutputBoundary {
    /** The log media view model. */
    private final LogMediaViewModel logMediaViewModel;

    /** Creates a presenter for log media output. */
    public LogMediaPresenter(
            final LogMediaViewModel inputLogMediaViewModel) {
        this.logMediaViewModel = inputLogMediaViewModel;
    }

    @Override
    public void prepareSuccessView(final LogMediaOutputData outputData) {
        final LogMediaState state = logMediaViewModel.getState();
        state.setMessage(outputData.getMessage());
        state.setError("");
        logMediaViewModel.setState(state);
        logMediaViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(final String error) {
        final LogMediaState state = logMediaViewModel.getState();
        state.setMessage("");
        state.setError(error);
        logMediaViewModel.setState(state);
        logMediaViewModel.firePropertyChanged();
    }
}
