package use_case.recommendation;

/**
 * One rating a user gave one title.
 *
 * Deliberately a small type of its own rather than {@code entity.Review}. The
 * review feature is being built separately, so depending on it here would tie
 * this use case to another branch's progress. All the recommendation algorithm
 * needs is which title and how many stars; whoever supplies the data maps their
 * own representation onto this in the data access layer.
 */
public class UserRating {

    private final int mediaId;
    private final double rating;

    /**
     * Creates a rating.
     *
     * @param mediaId the title that was rated
     * @param rating the stars given, on a 1-5 scale
     */
    public UserRating(final int mediaId, final double rating) {
        this.mediaId = mediaId;
        this.rating = rating;
    }

    /**
     * Returns the rated title's id.
     *
     * @return the media id
     */
    public int getMediaId() {
        return this.mediaId;
    }

    /**
     * Returns the stars given.
     *
     * @return the rating on a 1-5 scale
     */
    public double getRating() {
        return this.rating;
    }
}
