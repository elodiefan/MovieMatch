package use_case.review.create_review;

/**
 * Data access interface for checking whether a user may write a review.
 */
public interface CreateReviewUserDataAccessInterface {

    /**
     * Checks whether the user has already added the media to their watch history.
     * @param username the username
     * @param mediaId the media id
     * @param mediaType the media type
     * @return true if the media is in the user's watch history
     */
    boolean hasWatchedMedia(String username, int mediaId, String mediaType);
}
