package use_case.review.get_user_reviews;

import java.util.ArrayList;
import java.util.List;

import entity.Review;
import use_case.review.ReviewSummaryData;
import use_case.review.ReviewSummaryMapper;

/**
 * Output data for loading reviews written by one user.
 */
public final class GetUserReviewsOutputData {
    /** The reviews. */
    private final List<ReviewSummaryData> reviews;

    /**
     * Creates output data for loaded user reviews.
     */
    public GetUserReviewsOutputData(final List<Review> inputReviews) {
        this.reviews = ReviewSummaryMapper.toSummaries(inputReviews);
    }

    /**
     * Returns loaded user reviews.
     */
    public List<ReviewSummaryData> getReviews() {
        return new ArrayList<>(reviews);
    }
}
