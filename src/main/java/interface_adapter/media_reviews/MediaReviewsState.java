package interface_adapter.media_reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the media reviews panel.
 */
public final class MediaReviewsState {
    /**
     * The media id.
     */
    private int mediaId;
    /**
     * The media type.
     */
    private String mediaType = "";
    /**
     * The media title.
     */
    private String mediaTitle = "";
    /**
     * The release year.
     */
    private int releaseYear;
    /**
     * The poster path.
     */
    private String posterPath = "";
    /**
     * The reviews.
     */
    private List<MediaReviewRow> reviews = new ArrayList<>();
    /**
     * The selected review id.
     */
    private String selectedReviewId = "";
    /**
     * The media reviews error.
     */
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
     * @param inputMediaId the media id
     */
    public void setMediaId(final int inputMediaId) {
        this.mediaId = inputMediaId;
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
     * @param inputMediaType the media type
     */
    public void setMediaType(final String inputMediaType) {
        this.mediaType = inputMediaType;
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
     * @param inputMediaTitle the media title
     */
    public void setMediaTitle(final String inputMediaTitle) {
        this.mediaTitle = inputMediaTitle;
    }

    /**
     * Returns the release year.
     * @return the release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Sets the release year.
     * @param inputReleaseYear the release year
     */
    public void setReleaseYear(final int inputReleaseYear) {
        this.releaseYear = inputReleaseYear;
    }

    /**
     * Returns the poster path.
     * @return the poster path
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Sets the poster path.
     * @param inputPosterPath the poster path
     */
    public void setPosterPath(final String inputPosterPath) {
        this.posterPath = inputPosterPath;
    }

    /**
     * Returns the displayed review rows.
     * @return a copy of the review rows
     */
    public List<MediaReviewRow> getReviews() {
        return new ArrayList<>(reviews);
    }

    /**
     * Sets the displayed review rows.
     * @param inputReviews the review rows
     */
    public void setReviews(final List<MediaReviewRow> inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
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
     * @param inputSelectedReviewId the selected review id
     */
    public void setSelectedReviewId(final String inputSelectedReviewId) {
        this.selectedReviewId = inputSelectedReviewId;
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
     * @param inputMediaReviewsError the error message
     */
    public void setMediaReviewsError(final String inputMediaReviewsError) {
        this.mediaReviewsError = inputMediaReviewsError;
    }
}
