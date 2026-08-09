package interface_adapter.media_detail;

import java.util.ArrayList;
import java.util.List;

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
     * @param mediaId the media id
     * @param mediaType the media type
     * @param mediaTitle the media title
     * @param releaseYear the release year
     * @param averageRating the average rating
     * @param genreNames the genre names
     * @param language the language
     * @param overview the overview
     * @param posterPath the poster path
     */
    public void execute(final int mediaId, final String mediaType,
                        final String mediaTitle, final int releaseYear,
                        final double averageRating,
                        final List<String> genreNames,
                        final String language, final String overview,
                        final String posterPath) {
        final MediaDetailInputData inputData =
                new MediaDetailInputData(mediaId, mediaType, mediaTitle,
                        releaseYear, averageRating, genreNames, language,
                        overview, posterPath);

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
        execute(mediaId, mediaType, mediaTitle, releaseYear, 0,
                new ArrayList<>(), "", "", posterPath);
    }

    /**
     * Switches back to the search result view.
     */
    public void backToSearchResultView() {
        mediaDetailUseCaseInteractor.backToSearchResultView();
    }
}
