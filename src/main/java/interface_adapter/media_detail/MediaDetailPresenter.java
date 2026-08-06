package interface_adapter.media_detail;

import java.util.List;

import data_access.InMemoryReviewDataAccessObject;
import entity.Review;
import interface_adapter.ViewManagerModel;
import interface_adapter.media_reviews.MediaReviewsPresenter;
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
    private final MediaReviewsPresenter mediaReviewsPresenter;
    private final InMemoryReviewDataAccessObject reviewDataAccessObject;

    public MediaDetailPresenter(
            ViewManagerModel viewManagerModel,
            MediaDetailViewModel mediaDetailViewModel,
            MediaReviewsViewModel mediaReviewsViewModel,
            MediaReviewsPresenter mediaReviewsPresenter,
            InMemoryReviewDataAccessObject reviewDataAccessObject) {

        this.viewManagerModel = viewManagerModel;
        this.mediaDetailViewModel = mediaDetailViewModel;
        this.mediaReviewsViewModel = mediaReviewsViewModel;
        this.mediaReviewsPresenter = mediaReviewsPresenter;
        this.reviewDataAccessObject = reviewDataAccessObject;
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
        detailState.setGenres(outputData.getGenres());
        detailState.setLanguage(outputData.getLanguage());

        mediaDetailViewModel.setState(detailState);
        mediaDetailViewModel.firePropertyChanged();

        final List<Review> reviews =
                reviewDataAccessObject.getReviewsByMedia(
                        outputData.getMediaId(),
                        outputData.getMediaType()
                );

        final MediaReviewsState reviewsState =
                mediaReviewsViewModel.getState();

        reviewsState.setMediaId(outputData.getMediaId());
        reviewsState.setMediaType(outputData.getMediaType());
        reviewsState.setMediaTitle(outputData.getTitle());
        reviewsState.setReviews(
                mediaReviewsPresenter.prepareReviews(reviews)
        );
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

        reviewsState.setMediaReviewsError(
                mediaReviewsPresenter.prepareFailView(error)
        );

        mediaReviewsViewModel.setState(reviewsState);
        mediaReviewsViewModel.firePropertyChanged();
    }

    @Override
    public void backToSearchResultView() {
        viewManagerModel.setState(SearchResultViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }
}
