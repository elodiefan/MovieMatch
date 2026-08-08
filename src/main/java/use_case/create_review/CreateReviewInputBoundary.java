package use_case.create_review;

/**
 * Input boundary for creating a review.
 */
public interface CreateReviewInputBoundary {
    /**
     * Executes the use case.
     * @param mediaId the media id
     * @param mediaType the media type
     * @param mediaTitle the media title
     * @param authorUsername the author's username
     * @param authorDisplayName the author's display name
     * @param rating the review rating
     * @param reviewText the review text
     */
    void execute(int mediaId, String mediaType, String mediaTitle,
                 String authorUsername, String authorDisplayName,
                 double rating, String reviewText);
}
