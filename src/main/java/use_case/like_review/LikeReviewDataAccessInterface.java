package use_case.like_review;

/**
 * Data access interface for liking reviews.
 */
public interface LikeReviewDataAccessInterface {

    /**
     * Adds a user's like to one review.
     */
    boolean likeReview(String reviewId, String username);
}
