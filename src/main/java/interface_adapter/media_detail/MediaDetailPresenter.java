package interface_adapter.media_detail;

import interface_adapter.ViewManagerModel;
import interface_adapter.media_reviews.MediaReviewsState;
import interface_adapter.media_reviews.MediaReviewsViewModel;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.media_detail.MediaDetailOutputBoundary;
import use_case.media_detail.MediaDetailOutputData;

/**
 * Presenter for the Media Detail Use Case.
 */
public class MediaDetailPresenter implements MediaDetailOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final MediaDetailViewModel mediaDetailViewModel;

    private final MediaReviewsViewModel mediaReviewsViewModel;

    public MediaDetailPresenter(
            ViewManagerModel viewManagerModel,
            MediaDetailViewModel mediaDetailViewModel,
            MediaReviewsViewModel mediaReviewsViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.mediaDetailViewModel = mediaDetailViewModel;
        this.mediaReviewsViewModel = mediaReviewsViewModel;
    }

    @Override
    public void prepareSuccessView(MediaDetailOutputData outputData) {
        final MediaDetailState detailState =
                mediaDetailViewModel.getState();

        detailState.setMediaId(outputData.getMediaId());
        detailState.setMediaType(outputData.getMediaType());
        detailState.setTitle(outputData.getTitle());
        detailState.setReleaseYear(outputData.getReleaseYear());
        detailState.setAverageRating(outputData.getAverageRating());
        detailState.setGenreNames(outputData.getGenreNames());
        detailState.setLanguage(outputData.getLanguage());
        detailState.setOverview(outputData.getOverview());
        detailState.setPosterPath(outputData.getPosterPath());

        mediaDetailViewModel.setState(detailState);
        mediaDetailViewModel.firePropertyChanged();

        final MediaReviewsState reviewsState =
                mediaReviewsViewModel.getState();

        reviewsState.setMediaId(outputData.getMediaId());
        reviewsState.setMediaType(outputData.getMediaType());
        reviewsState.setMediaTitle(outputData.getTitle());
        reviewsState.setReleaseYear(outputData.getReleaseYear());
        reviewsState.setPosterPath(outputData.getPosterPath());
        reviewsState.setMediaReviewsError(null);

        mediaReviewsViewModel.setState(reviewsState);
        mediaReviewsViewModel.firePropertyChanged();

        viewManagerModel.setState(MediaDetailViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final MediaReviewsState reviewsState =
                mediaReviewsViewModel.getState();

        reviewsState.setMediaReviewsError(error);

        mediaReviewsViewModel.setState(reviewsState);
        mediaReviewsViewModel.firePropertyChanged();
    }

    @Override
    public void backToSearchResultView() {
        viewManagerModel.setState(SearchResultViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }
}
