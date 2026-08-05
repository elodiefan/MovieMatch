package interface_adapter.media_reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the media reviews panel.
 */
public class MediaReviewsState {
    private int mediaId;
    private String mediaType = "";
    private String mediaTitle = "";
    private List<MediaReviewsPresenter.MediaReviewRow> reviews =
            new ArrayList<>();
    private String selectedReviewId = "";
    private String mediaReviewsError;

    /**
     * Returns the media id.
     * @return the media id
     */
    public int getMediaId() {
        return mediaId;
    }

    /**
     * Sets the media id.
     * @param mediaId the media id
     */
    public void setMediaId(final int mediaId) {
        this.mediaId = mediaId;
    }

    /**
     * Returns the media type.
     * @return the media type
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets the media type.
     * @param mediaType the media type
     */
    public void setMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Returns the media title.
     * @return the media title
     */
    public String getMediaTitle() {
        return mediaTitle;
    }

    /**
     * Sets the media title.
     * @param mediaTitle the media title
     */
    public void setMediaTitle(final String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    /**
     * Returns the displayed review rows.
     * @return a copy of the review rows
     */
    public List<MediaReviewsPresenter.MediaReviewRow> getReviews() {
        return new ArrayList<>(reviews);
    }

    /**
     * Sets the displayed review rows.
     * @param reviews the review rows
     */
    public void setReviews(
            final List<MediaReviewsPresenter.MediaReviewRow> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }

    /**
     * Returns the selected review id.
     * @return the selected review id
     */
    public String getSelectedReviewId() {
        return selectedReviewId;
    }

    /**
     * Sets the selected review id.
     * @param selectedReviewId the selected review id
     */
    public void setSelectedReviewId(final String selectedReviewId) {
        this.selectedReviewId = selectedReviewId;
    }

    /**
     * Returns the current media reviews error message.
     * @return the error message
     */
    public String getMediaReviewsError() {
        return mediaReviewsError;
    }

    /**
     * Sets the current media reviews error message.
     * @param mediaReviewsError the error message
     */
    public void setMediaReviewsError(final String mediaReviewsError) {
        this.mediaReviewsError = mediaReviewsError;
    }
}
