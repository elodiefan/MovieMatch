package use_case.review.delete_review;

/** Output data for deleting a review. */
public final class DeleteReviewOutputData {
    /** The deleted. */
    private final boolean deleted;

    /** Handles this review or comment operation. */
    public DeleteReviewOutputData(final boolean inputDeleted) {
        this.deleted = inputDeleted;
    }

    /** Handles this review or comment operation. */
    public boolean isDeleted() {
        return deleted;
    }
}
