package use_case.media_detail;

import entity.Media;
import entity.Movie;

/** The Media Detail Interactor. */
public class MediaDetailInteractor implements MediaDetailInputBoundary {

    private final MediaDetailOutputBoundary mediaDetailPresenter;

    public MediaDetailInteractor(
            MediaDetailOutputBoundary mediaDetailPresenter) {
        this.mediaDetailPresenter = mediaDetailPresenter;
    }

    @Override
    public void execute(MediaDetailInputData inputData) {

        final Media media = inputData.getMedia();

        if (media == null) {
            mediaDetailPresenter.prepareFailView(
                    "Unable to display media details."
            );
        }
        else {

            final String mediaType;

            if (media instanceof Movie) {
                mediaType = "movie";
            }
            else {
                mediaType = "tv";
            }

            final MediaDetailOutputData outputData =
                    new MediaDetailOutputData(
                            media.getID(),
                            mediaType,
                            media.getTitle(),
                            media.getReleaseYear(),
                            media.getAverageRating(),
                            media.getGenres(),
                            media.getLanguage()
                    );

            mediaDetailPresenter.prepareSuccessView(outputData);
        }
    }

    @Override
    public void backToSearchResultView() {
        mediaDetailPresenter.backToSearchResultView();
    }
}
