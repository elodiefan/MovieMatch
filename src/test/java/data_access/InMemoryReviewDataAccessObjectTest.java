package data_access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import entity.Review;
import org.junit.jupiter.api.Test;

/**
 * Tests for the in-memory review data access object.
 */
public class InMemoryReviewDataAccessObjectTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void saveAndGetReviewById() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final Review review = makeReview("review-1", "elodie", 550,
                "movie", TIME);

        dao.saveReview(review);

        assertTrue(dao.existsByReviewId("review-1"));
        assertEquals(review, dao.getReviewById("review-1").get());
        assertFalse(dao.getReviewById("missing").isPresent());
    }

    @Test
    void getReviewsByUsernameFiltersReviews() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview("review-1", "elodie", 550, "movie", TIME));
        dao.saveReview(makeReview("review-2", "lily", 550, "movie", TIME));
        dao.saveReview(makeReview("review-3", "elodie", 551, "movie", TIME));

        final List<Review> reviews = dao.getReviewsByUsername("elodie");

        assertEquals(2, reviews.size());
        assertEquals("review-1", reviews.get(0).getReviewId());
        assertEquals("review-3", reviews.get(1).getReviewId());
    }

    @Test
    void getReviewsByMediaFiltersByMediaIdAndType() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        dao.saveReview(makeReview("review-1", "elodie", 550, "movie", TIME));
        dao.saveReview(makeReview("review-2", "lily", 550, "tv", TIME));
        dao.saveReview(makeReview("review-3", "yidan", 551, "movie", TIME));

        final List<Review> reviews = dao.getReviewsByMedia(550, "movie");

        assertEquals(1, reviews.size());
        assertEquals("review-1", reviews.get(0).getReviewId());
    }

    @Test
    void editDeleteLikeAndUnlikeReview() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();
        final Review review = makeReview("review-1", "elodie", 550,
                "movie", TIME);
        dao.saveReview(review);

        assertTrue(dao.editReview("review-1", 75.0, "Updated.",
                TIME.plusHours(1)));
        assertEquals(75.0, review.getRating());
        assertEquals("Updated.", review.getReviewText());
        assertTrue(dao.likeReview("review-1", "lily"));
        assertEquals(1, review.getLikeCount());
        assertTrue(dao.unlikeReview("review-1", "lily"));
        assertEquals(0, review.getLikeCount());
        assertTrue(dao.deleteReview("review-1"));
        assertFalse(dao.existsByReviewId("review-1"));
    }

    @Test
    void operationsReturnFalseForMissingReview() {
        final InMemoryReviewDataAccessObject dao =
                new InMemoryReviewDataAccessObject();

        assertFalse(dao.editReview("missing", 75.0, "Updated.", TIME));
        assertFalse(dao.deleteReview("missing"));
        assertFalse(dao.likeReview("missing", "lily"));
        assertFalse(dao.unlikeReview("missing", "lily"));
    }

    private Review makeReview(String reviewId, String username, int mediaId,
                              String mediaType, ZonedDateTime createdAt) {
        return new Review(reviewId, mediaId, mediaType, "Media",
                username, username, 90.0, "Great.", createdAt, createdAt,
                "moviematch", new HashSet<>());
    }
}
