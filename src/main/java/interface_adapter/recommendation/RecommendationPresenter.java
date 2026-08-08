package interface_adapter.recommendation;

import javax.swing.SwingUtilities;

import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.RecommendationOutputData;

/**
 * Presents recommendations onto one view model.
 */
public class RecommendationPresenter implements RecommendationOutputBoundary {

    private final RecommendationViewModel recommendationViewModel;

    public RecommendationPresenter(RecommendationViewModel recommendationViewModel) {
        this.recommendationViewModel = recommendationViewModel;
    }

    @Override
    public void presentRecommendations(RecommendationOutputData outputData) {
        onUiThread(() -> {
            final RecommendationState state = recommendationViewModel.getState();
            state.setRecommendations(outputData.getRecommendations());
            state.setSections(outputData.getSections());
            state.setUsername(outputData.getUsername());
            state.setRecommendationError(null);
            state.setLoaded(true);

            recommendationViewModel.setState(state);
            recommendationViewModel.firePropertyChanged();
        });
    }

    @Override
    public void prepareFailView(String errorMessage) {
        onUiThread(() -> {
            final RecommendationState state = recommendationViewModel.getState();
            state.setRecommendationError(errorMessage);
            state.setLoaded(true);

            recommendationViewModel.setState(state);
            recommendationViewModel.firePropertyChanged();
        });
    }

    private void onUiThread(Runnable update) {
        if (SwingUtilities.isEventDispatchThread()) {
            update.run();
        }
        else {
            SwingUtilities.invokeLater(update);
        }
    }
}
