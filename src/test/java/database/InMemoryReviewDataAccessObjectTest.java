package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Review;

class InMemoryReviewDataAccessObjectTest {

    private static final ZonedDateTime CREATED = ZonedDateTime.of(
            2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    @Test
    void saveFindAndFilterReviews() {
        final InMemoryReviewDataAccessObject dataAccess =
                new InMemoryReviewDataAccessObject();
        final Review first = review("r1", 10, "movie", "alice", 8.0);
        final Review second = review("r2", 20, "tv", "bob", 7.0);
        dataAccess.saveReview(first);
        dataAccess.saveReview(second);

        assertTrue(dataAccess.existsByReviewId("r1"));
        assertFalse(dataAccess.existsByReviewId("missing"));
        assertEquals(first, dataAccess.getReviewById("r1").orElseThrow());
        assertTrue(dataAccess.getReviewById("missing").isEmpty());
        assertEquals(List.of(first), dataAccess.getReviewsByUsername("alice"));
        assertTrue(dataAccess.getReviewsByUsername("nobody").isEmpty());
        assertEquals(List.of(second), dataAccess.getReviewsByMedia(20, "tv"));
        assertTrue(dataAccess.getReviewsByMedia(20, "movie").isEmpty());
        assertEquals(List.of(first, second), dataAccess.getAllReviews());
    }

    @Test
    void mutationsCoverExistingAndMissingReviews() {
        final InMemoryReviewDataAccessObject dataAccess =
                new InMemoryReviewDataAccessObject();
        final Review review = review("r1", 10, "movie", "alice", 8.0);
        dataAccess.saveReview(review);
        final ZonedDateTime updated = CREATED.plusDays(1);

        assertTrue(dataAccess.editReview("r1", 9.0, "better", updated));
        assertEquals(9.0, review.getRating());
        assertEquals("better", review.getReviewText());
        assertFalse(dataAccess.editReview("missing", 1.0, "x", updated));
        assertTrue(dataAccess.likeReview("r1", "bob"));
        assertTrue(review.getLikedByUsernames().contains("bob"));
        assertFalse(dataAccess.likeReview("missing", "bob"));
        assertTrue(dataAccess.unlikeReview("r1", "bob"));
        assertFalse(review.getLikedByUsernames().contains("bob"));
        assertFalse(dataAccess.unlikeReview("missing", "bob"));
        assertFalse(dataAccess.deleteReview("missing"));
        assertTrue(dataAccess.deleteReview("r1"));
        assertFalse(dataAccess.existsByReviewId("r1"));
        dataAccess.close();
    }

    @Test
    void recommendationRatingsContainOnlyRequestedUser() {
        final InMemoryReviewDataAccessObject dataAccess =
                new InMemoryReviewDataAccessObject();
        dataAccess.saveReview(review("r1", 10, "movie", "alice", 8.5));
        dataAccess.saveReview(review("r2", 20, "tv", "alice", 6.5));
        dataAccess.saveReview(review("r3", 30, "movie", "bob", 9.5));

        assertEquals(2, dataAccess.findReviewRatingsByUser("alice").size());
        assertEquals(10, dataAccess.findReviewRatingsByUser("alice")
                .get(0).getMediaId());
        assertEquals(8.5, dataAccess.findReviewRatingsByUser("alice")
                .get(0).getRating());
    }

    private static Review review(String id, int mediaId, String mediaType,
                                 String username, double rating) {
        return new Review(id, mediaId, mediaType, "Title", username,
                "Display", rating, "Text", CREATED, CREATED,
                "local", Set.of());
    }
}
