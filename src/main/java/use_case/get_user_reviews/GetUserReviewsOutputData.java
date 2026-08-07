package use_case.get_user_reviews;

import java.util.ArrayList;
import java.util.List;

import use_case.get_media_reviews.ReviewSummaryData;

/**
 * Output data for loading reviews written by one user.
 */
public final class GetUserReviewsOutputData {
    /** The reviews. */
    private final List<ReviewSummaryData> reviews;

    /**
     * Creates output data for loaded user reviews.
     */
    public GetUserReviewsOutputData(final List<ReviewSummaryData>
                                            inputReviews) {
        this.reviews = new ArrayList<>(inputReviews);
    }

    /**
     * Returns loaded user reviews.
     */
    public List<ReviewSummaryData> getReviews() {
        return new ArrayList<>(reviews);
    }
}
