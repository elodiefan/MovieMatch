package interface_adapter.recommendation;

import javax.swing.SwingUtilities;

import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.RecommendationOutputData;

/**
 * Presents recommendations onto one view model.
 *
 * Implements a boundary owned by the use case, so the interactor reports its
 * result without knowing whether a home page strip or a full screen is
 * listening. Two of these are built over the same interactor class, one per
 * screen, which is what the output boundary's own javadoc anticipates.
 *
 * Recommendations are produced off the UI thread because they reach TMDB and
 * possibly Gemini, so updates are handed back to it here rather than in the
 * view.
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
