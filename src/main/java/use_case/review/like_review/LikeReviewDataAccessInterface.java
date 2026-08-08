package use_case.review.like_review;

/**
 * Data access interface for liking reviews.
 */
public interface LikeReviewDataAccessInterface {

    /**
     * Adds a user's like to one review.
     * @param reviewId the id of the review to like
     * @param username the username of the user liking the review
     * @return true if the review was found and liked
     */
    boolean likeReview(String reviewId, String username);
}
