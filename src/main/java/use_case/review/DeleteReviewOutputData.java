package use_case.review;

/**
 * Output data for deleting a review.
 */
public class DeleteReviewOutputData {
    private final boolean deleted;

    public DeleteReviewOutputData(final boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
