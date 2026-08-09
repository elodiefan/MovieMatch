package database;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import entity.Review;
import use_case.comment.get_user_comments.GetUserCommentsReviewDataAccessInterface;
import use_case.recommendation.ReviewedMediaRatingDataAccessInterface;
import use_case.recommendation.UserRating;
import use_case.review.create_review.CreateReviewDataAccessInterface;
import use_case.review.delete_review.DeleteReviewDataAccessInterface;
import use_case.review.edit_review.EditReviewDataAccessInterface;
import use_case.review.get_media_reviews.GetMediaReviewsDataAccessInterface;
import use_case.review.get_user_reviews.GetUserReviewsDataAccessInterface;
import use_case.review.like_review.LikeReviewDataAccessInterface;
import use_case.review.unlike_review.UnlikeReviewDataAccessInterface;

/**
 * In-memory data access object for review data.
 */
public class InMemoryReviewDataAccessObject implements
        CreateReviewDataAccessInterface,
        DeleteReviewDataAccessInterface,
        EditReviewDataAccessInterface,
        GetMediaReviewsDataAccessInterface,
        GetUserCommentsReviewDataAccessInterface,
        GetUserReviewsDataAccessInterface,
        LikeReviewDataAccessInterface,
        ReviewedMediaRatingDataAccessInterface,
        UnlikeReviewDataAccessInterface {
    private final Map<String, Review> reviews = new LinkedHashMap<>();
    // creates the actual in memory database for reviews
    // LinkedHashMap instead of regular HashMap because LinkedHashMap remembers insertion order
    // so later if we call getAllReviews() they will be returned in order

    /**
     * Saves a review.
     * @param review the review to save
     */
    public void saveReview(Review review) {
        reviews.put(review.getReviewId(), review);
        // reviews.put(key, value)
    }

    /**
     * Returns whether a review exists.
     * @param reviewId the review id to check
     * @return true if a review with this id exists
     */
    public boolean existsByReviewId(String reviewId) {
        return reviews.containsKey(reviewId);
    }

    /**
     * Returns a review by id.
     * @param reviewId the review id to search for
     * @return the review, if it exists
     */
    public Optional<Review> getReviewById(String reviewId) {
        return Optional.ofNullable(reviews.get(reviewId));
        // Optional.ofNullable(...) is used because a review could exist or not
        // exist, so this method
        // is there to make sure it will return an Optional with the review if it exists, otherwise
        // it will return an empty Optional
    }

    /**
     * Returns all reviews written by a user.
     * @param username the author's username
     * @return the matching reviews
     */
    public List<Review> getReviewsByUsername(String username) {
        final List<Review> matchingReviews = new ArrayList<>();
        // so that this variable name cannot be reassigned to a different list later

        for (Review review : reviews.values()) {
            if (review.getAuthorUsername().equals(username)) {
                matchingReviews.add(review);
            }
        }

        return matchingReviews;
    }

    /**
     * Returns all reviews for one media item.
     * @param mediaId the media id
     * @param mediaType the media type
     * @return the matching reviews
     */
    public List<Review> getReviewsByMedia(int mediaId, String mediaType) {
        final List<Review> matchingReviews = new ArrayList<>();

        for (Review review : reviews.values()) {
            if (review.getMediaId() == mediaId && review.getMediaType().equals(mediaType)) {
                matchingReviews.add(review);
            }
        }

        return matchingReviews;
    }

    /**
     * Updates an existing review.
     * @param reviewId the review id
     * @param newRating the updated rating
     * @param newReviewText the updated review text
     * @param newUpdatedAt the updated timestamp
     * @return true if the review was updated
     */
    public boolean editReview(String reviewId, double newRating, String newReviewText,
                              ZonedDateTime newUpdatedAt) {
        final Optional<Review> review = getReviewById(reviewId);
        final boolean reviewExists = review.isPresent();

        if (reviewExists) {
            review.get().edit(newRating, newReviewText, newUpdatedAt);
        }

        return reviewExists;
    }

    /**
     * Deletes a review.
     * @param reviewId the review id
     * @return true if the review was deleted
     */
    public boolean deleteReview(String reviewId) {
        final boolean reviewExists = existsByReviewId(reviewId);

        if (reviewExists) {
            reviews.remove(reviewId);
        }

        return reviewExists;
        // a user cannot only delete the rating while still keep the review text
        // a review can have a rating without review text, but not vice versa
        // if only "delete" the review text while keeping the rating, that's an edit, not delete
        // delete deletes the whole thing

        // also note that editing a rating to 0% means they gave this media a rating of 0
        // this doesn't mean the rating got deleted
    }

    /**
     * Adds a user's like to a review.
     * @param reviewId the review id
     * @param username the username liking the review
     * @return true if the review exists
     */
    public boolean likeReview(String reviewId, String username) {
        final Optional<Review> review = getReviewById(reviewId);
        final boolean reviewExists = review.isPresent();

        if (reviewExists) {
            review.get().like(username);
        }

        return reviewExists;
    }

    /**
     * Removes a user's like from a review.
     * @param reviewId the review id
     * @param username the username unliking the review
     * @return true if the review exists
     */
    public boolean unlikeReview(String reviewId, String username) {
        final Optional<Review> review = getReviewById(reviewId);
        final boolean reviewExists = review.isPresent();

        if (reviewExists) {
            review.get().unlike(username);
        }

        return reviewExists;
    }

    /**
     * Returns all saved reviews.
     * @return all reviews
     */
    public List<Review> getAllReviews() {
        return new ArrayList<>(reviews.values());
    }

    @Override
    public List<UserRating> findReviewRatingsByUser(final String username) {
        final List<UserRating> userRatings = new ArrayList<>();
        for (Review review : getReviewsByUsername(username)) {
            userRatings.add(new UserRating(review.getMediaId(),
                    review.getRating()));
        }
        return userRatings;
    }

    /**
     * Closes the in-memory review store.
     */
    public void close() {
        // No resources to free for an in-memory store.
    }
}
