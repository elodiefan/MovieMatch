package interface_adapter.media_detail;

import entity.Media;
import use_case.media_detail.MediaDetailInputBoundary;
import use_case.media_detail.MediaDetailInputData;

/** Controller for the Media Detail Use Case. */
public class MediaDetailController {

    private final MediaDetailInputBoundary mediaDetailUseCaseInteractor;

    public MediaDetailController(
            MediaDetailInputBoundary mediaDetailUseCaseInteractor) {
        this.mediaDetailUseCaseInteractor = mediaDetailUseCaseInteractor;
    }

    /** Displays details for the selected media. */
    public void execute(Media media) {
        final MediaDetailInputData inputData =
                new MediaDetailInputData(media);

        mediaDetailUseCaseInteractor.execute(inputData);
    }

    /** Switches back to the search result view. */
    public void backToSearchResultView() {
        mediaDetailUseCaseInteractor.backToSearchResultView();
    }
}
