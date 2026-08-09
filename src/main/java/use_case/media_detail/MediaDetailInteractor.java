package use_case.media_detail;

/**
 * The Media Detail Interactor.
 */
public class MediaDetailInteractor implements MediaDetailInputBoundary {

    private final MediaDetailOutputBoundary mediaDetailPresenter;

    public MediaDetailInteractor(
            MediaDetailOutputBoundary mediaDetailPresenter) {
        this.mediaDetailPresenter = mediaDetailPresenter;
    }

    @Override
    public void execute(MediaDetailInputData inputData) {

        if (inputData == null) {
            mediaDetailPresenter.prepareFailView(
                    "Unable to display media details."
            );
        }
        else {
            final MediaDetailOutputData outputData =
                    new MediaDetailOutputData(
                            inputData.getMediaId(),
                            inputData.getMediaType(),
                            inputData.getTitle(),
                            inputData.getReleaseYear(),
                            inputData.getAverageRating(),
                            inputData.getGenreNames(),
                            inputData.getLanguage(),
                            inputData.getOverview(),
                            inputData.getPosterPath()
                    );

            mediaDetailPresenter.prepareSuccessView(outputData);
        }
    }

    @Override
    public void backToSearchResultView() {
        mediaDetailPresenter.backToSearchResultView();
    }
}
