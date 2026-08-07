package use_case.get_media_reviews;

import java.util.ArrayList;
import java.util.List;

import entity.Review;

/**
 * Converts review entities into display-safe review data.
 */
public final class ReviewSummaryMapper {

    private ReviewSummaryMapper() {
    }

    /**
     * Converts one review entity.
     */
    public static ReviewSummaryData toSummary(final Review review) {
        return new ReviewSummaryData(review.getReviewId(), review.getMediaId(),
                review.getMediaType(), review.getMediaTitle(),
                review.getAuthorUsername(), review.getAuthorDisplayName(),
                review.getRating(), review.getReviewText(),
                review.getCreatedAt(), review.getUpdatedAt(),
                review.getLikeCount(), review.getSource());
    }

    /**
     * Converts review entities into summaries.
     */
    public static List<ReviewSummaryData> toSummaries(
            final List<Review> reviews) {
        final List<ReviewSummaryData> summaries = new ArrayList<>();
        if (reviews != null) {
            for (Review review : reviews) {
                if (review != null) {
                    summaries.add(toSummary(review));
                }
            }
        }
        return summaries;
    }
}
