package use_case.review.create_review;

/**
 * Input boundary for creating a review.
 */
public interface CreateReviewInputBoundary {
    /**
     * Executes the use case.
     * @param mediaId the media id
     * @param mediaType the media type
     * @param mediaTitle the media title
     * @param releaseYear the release year
     * @param posterPath the poster path
     * @param authorUsername the author's username
     * @param authorDisplayName the author's display name
     * @param rating the review rating
     * @param reviewText the review text
     */
    void execute(int mediaId, String mediaType, String mediaTitle,
                 int releaseYear, String posterPath,
                 String authorUsername, String authorDisplayName,
                 double rating, String reviewText);

    /**
     * Checks whether the user may start writing a review for this media item.
     * @param mediaId the media id
     * @param mediaType the media type
     * @param authorUsername the author's username
     * @return true if the user may write a review
     */
    boolean canCreateReview(int mediaId, String mediaType,
                            String authorUsername);
}
