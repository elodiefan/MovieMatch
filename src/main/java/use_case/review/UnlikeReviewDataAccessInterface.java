package use_case.review;

/**
 * Data access interface for unliking reviews.
 */
public interface UnlikeReviewDataAccessInterface {

    /**
     * Removes a user's like from one review.
     * @param reviewId the id of the review to unlike
     * @param username the username of the user unliking the review
     * @return true if the review was found and unliked
     */
    boolean unlikeReview(String reviewId, String username);
}
