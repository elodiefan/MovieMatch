package interface_adapter.recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import use_case.recommendation.RecommendationOutputBoundary;
import use_case.recommendation.RecommendationOutputData;

/**
 * Presents recommendations onto one view model.
 */
public class RecommendationPresenter implements RecommendationOutputBoundary {

    private final RecommendationViewModel recommendationViewModel;
    private final Executor uiExecutor;

    public RecommendationPresenter(RecommendationViewModel recommendationViewModel,
                             Executor uiExecutor) {
        this.recommendationViewModel = recommendationViewModel;
        this.uiExecutor = uiExecutor;
    }

    @Override
    public void presentRecommendations(RecommendationOutputData outputData) {
        onUiThread(() -> {
            final RecommendationState state = recommendationViewModel.getState();
            state.setRecommendations(toRows(outputData));
            state.setSections(toSections(outputData));
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
        uiExecutor.execute(update);
    }

    private List<RecommendationRow> toRows(final RecommendationOutputData outputData) {
        final List<RecommendationRow> rows = new ArrayList<>();
        for (int index = 0; index < outputData.getRecommendationCount(); index++) {
            rows.add(new RecommendationRow(
                    outputData.getRecommendationMediaId(index),
                    outputData.getRecommendationTitle(index),
                    outputData.getRecommendationReleaseYear(index),
                    outputData.getRecommendationScore(index),
                    outputData.getRecommendationPrimaryGenre(index),
                    outputData.getRecommendationExplanation(index),
                    outputData.getRecommendationPosterPath(index)
            ));
        }
        return rows;
    }

    private List<RecommendationSection> toSections(
            final RecommendationOutputData outputData) {
        final List<RecommendationSection> displaySections = new ArrayList<>();
        for (int sectionIndex = 0;
             sectionIndex < outputData.getSectionCount();
             sectionIndex++) {
            displaySections.add(new RecommendationSection(
                    outputData.getSectionHeading(sectionIndex),
                    toSectionRows(outputData, sectionIndex)
            ));
        }
        return displaySections;
    }

    private List<RecommendationRow> toSectionRows(
            final RecommendationOutputData outputData,
            final int sectionIndex) {
        final List<RecommendationRow> rows = new ArrayList<>();
        for (int index = 0;
             index < outputData.getSectionRecommendationCount(sectionIndex);
             index++) {
            rows.add(new RecommendationRow(
                    outputData.getSectionRecommendationMediaId(sectionIndex, index),
                    outputData.getSectionRecommendationTitle(sectionIndex, index),
                    outputData.getSectionRecommendationReleaseYear(sectionIndex, index),
                    outputData.getSectionRecommendationScore(sectionIndex, index),
                    outputData.getSectionRecommendationPrimaryGenre(sectionIndex, index),
                    outputData.getSectionRecommendationExplanation(sectionIndex, index),
                    outputData.getSectionRecommendationPosterPath(sectionIndex, index)
            ));
        }
        return rows;
    }
}
