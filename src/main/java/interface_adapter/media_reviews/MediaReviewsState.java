package interface_adapter.media_reviews;

import java.util.ArrayList;
import java.util.List;

/** State for the media reviews panel. */
public final class MediaReviewsState {
    /** The media id. */
    private int mediaId;
    /** The media type. */
    private String mediaType = "";
    /** The media title. */
    private String mediaTitle = "";
    /** The reviews. */
    private List<MediaReviewRow> reviews = new ArrayList<>();
    /** The selected review id. */
    private String selectedReviewId = "";
    /** The media reviews error. */
    private String mediaReviewsError;

    /** Returns the media id. */
    public int getMediaId() {
        return mediaId;
    }

    /** Sets the media id. */
    public void setMediaId(final int inputMediaId) {
        this.mediaId = inputMediaId;
    }

    /** Returns the media type. */
    public String getMediaType() {
        return mediaType;
    }

    /** Sets the media type. */
    public void setMediaType(final String inputMediaType) {
        this.mediaType = inputMediaType;
    }

    /** Returns the media title. */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /** Sets the media title. */
    public void setMediaTitle(final String inputMediaTitle) {
        this.mediaTitle = inputMediaTitle;
    }

    /** Returns the displayed review rows. */
    public List<MediaReviewRow> getReviews() {
        return new ArrayList<>(reviews);
    }

    /** Sets the displayed review rows. */
    public void setReviews(final List<MediaReviewRow> inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /** Returns the selected review id. */
    public String getSelectedReviewId() {
        return selectedReviewId;
    }

    /** Sets the selected review id. */
    public void setSelectedReviewId(final String inputSelectedReviewId) {
        this.selectedReviewId = inputSelectedReviewId;
    }

    /** Returns the current media reviews error message. */
    public String getMediaReviewsError() {
        return mediaReviewsError;
    }

    /** Sets the current media reviews error message. */
    public void setMediaReviewsError(final String inputMediaReviewsError) {
        this.mediaReviewsError = inputMediaReviewsError;
    }
}
