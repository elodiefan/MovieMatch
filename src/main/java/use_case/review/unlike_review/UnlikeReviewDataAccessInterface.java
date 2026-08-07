package use_case.review.unlike_review;

/** Data access interface for unliking reviews. */
public interface UnlikeReviewDataAccessInterface {

    /** Removes a user's like from one review. */
    boolean unlikeReview(String reviewId, String username);
}
