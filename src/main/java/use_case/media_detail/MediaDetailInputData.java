package use_case.media_detail;

import entity.Media;

/**
 * Input data for the Media Detail Use Case.
 */
public class MediaDetailInputData {

    private final Media media;

    public MediaDetailInputData(Media media) {
        this.media = media;
    }

    /**
     * Returns the selected media.
     */
    public Media getMedia() {
        return media;
    }
}
