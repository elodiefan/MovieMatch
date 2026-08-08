package interface_adapter.media_detail;

import java.util.ArrayList;

import entity.Media;
import entity.Movie;
import entity.TVShow;
import use_case.media_detail.MediaDetailInputBoundary;
import use_case.media_detail.MediaDetailInputData;

/**
 * Controller for the Media Detail Use Case.
 */
public class MediaDetailController {

    private final MediaDetailInputBoundary mediaDetailUseCaseInteractor;

    public MediaDetailController(
            MediaDetailInputBoundary mediaDetailUseCaseInteractor) {
        this.mediaDetailUseCaseInteractor = mediaDetailUseCaseInteractor;
    }

    /**
     * Displays details for the selected media.
     *
     * @param media the selected media
     */
    public void execute(Media media) {
        final MediaDetailInputData inputData =
                new MediaDetailInputData(media);

        mediaDetailUseCaseInteractor.execute(inputData);
    }

    /**
     * Displays details for saved media using raw view input.
     * @param mediaId the media id
     * @param mediaType the media type
     * @param mediaTitle the media title
     * @param releaseYear the release year
     * @param posterPath the poster path
     */
    public void execute(final int mediaId, final String mediaType,
                        final String mediaTitle, final int releaseYear,
                        final String posterPath) {
        execute(createMedia(mediaId, mediaType, mediaTitle, releaseYear,
                posterPath));
    }

    private Media createMedia(final int mediaId, final String mediaType,
                              final String mediaTitle,
                              final int releaseYear,
                              final String posterPath) {
        final Media media;
        if ("tv".equals(mediaType)) {
            media = new TVShow(mediaId, mediaTitle, releaseYear, 0,
                    new ArrayList<>(), "", new ArrayList<>(), 0, 0, "",
                    posterPath);
        } else {
            media = new Movie(mediaId, mediaTitle, releaseYear, 0,
                    new ArrayList<>(), "", new ArrayList<>(), 0, "",
                    posterPath);
        }
        return media;
    }

    /**
     * Switches back to the search result view.
     */
    public void backToSearchResultView() {
        mediaDetailUseCaseInteractor.backToSearchResultView();
    }
}
