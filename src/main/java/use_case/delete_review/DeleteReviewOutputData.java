package use_case.delete_review;

/**
 * Output data for deleting a review.
 */
public final class DeleteReviewOutputData {
    /**
     * The deleted.
     */
    private final boolean deleted;

    /**
     * Handles this review or comment operation.
     * @param inputDeleted the inputDeleted
     */
    public DeleteReviewOutputData(final boolean inputDeleted) {
        this.deleted = inputDeleted;
    }

    /**
     * Handles this review or comment operation.
     * @return the result
     */
    public boolean isDeleted() {
        return deleted;
    }
}
