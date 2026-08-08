package use_case.recommendation;

/**
 * One rating a user gave one title.
 */
public class UserRating {

    private final int mediaId;
    private final double rating;

    /**
     * Creates a rating.
     *
     * @param mediaId the media id
     * @param rating the rating
     */
    public UserRating(final int mediaId, final double rating) {
        this.mediaId = mediaId;
        this.rating = rating;
    }

    /**
     * Returns the rated title's id.
     *
     * @return the get media id
     */
    public int getMediaId() {
        return this.mediaId;
    }

    /**
     * Returns the stars given.
     *
     * @return the get rating
     */
    public double getRating() {
        return this.rating;
    }
}
