package use_case.delete_account;

/**
 * Data access interface for the Delete Account Use Case.
 */
public interface DeleteAccountReviewDataAccessInterface {

    /**
     * Deletes a review.
     * @param authorUsername author's username
     */
    void deleteAllReviews(String authorUsername);
}
