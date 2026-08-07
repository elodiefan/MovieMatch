package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Review entity.
 */
public class ReviewTest {

    @Test
    void constructorStoresReviewFields() {
        final ZonedDateTime createdAt = ZonedDateTime.of(2026, 8, 7, 10, 0,
                0, 0, ZoneId.of("America/Toronto"));
        final ZonedDateTime updatedAt = createdAt.plusHours(1);
        final Set<String> likedBy = new HashSet<>();
        likedBy.add("lily");

        final Review review = new Review("review-1", 550, "movie",
                "Fight Club", "elodie", "Elodie", 95.0,
                "Loved it.", createdAt, updatedAt, "moviematch", likedBy);

        assertEquals("review-1", review.getReviewId());
        assertEquals("review-1", review.getContentId());
        assertEquals(550, review.getMediaId());
        assertEquals("movie", review.getMediaType());
        assertEquals("Fight Club", review.getMediaTitle());
        assertEquals("elodie", review.getAuthorUsername());
        assertEquals("Elodie", review.getAuthorDisplayName());
        assertEquals(95.0, review.getRating());
        assertEquals("Loved it.", review.getReviewText());
        assertEquals(createdAt, review.getCreatedAt());
        assertEquals(updatedAt, review.getUpdatedAt());
        assertEquals("moviematch", review.getSource());
        assertEquals(1, review.getLikeCount());
    }

    @Test
    void editUpdatesEditableFieldsOnly() {
        final ZonedDateTime createdAt = ZonedDateTime.of(2026, 8, 7, 10, 0,
                0, 0, ZoneId.of("America/Toronto"));
        final ZonedDateTime originalUpdatedAt = createdAt.plusHours(1);
        final ZonedDateTime newUpdatedAt = createdAt.plusHours(2);
        final Review review = new Review("review-1", 550, "movie",
                "Fight Club", "elodie", "Elodie", 95.0,
                "Loved it.", createdAt, originalUpdatedAt, "moviematch",
                new HashSet<>());

        review.edit(80.0, "Still good.", newUpdatedAt);

        assertEquals("review-1", review.getReviewId());
        assertEquals("elodie", review.getAuthorUsername());
        assertEquals(80.0, review.getRating());
        assertEquals("Still good.", review.getReviewText());
        assertEquals(newUpdatedAt, review.getUpdatedAt());
        assertEquals(createdAt, review.getCreatedAt());
    }

    @Test
    void likesCanBeAddedAndRemoved() {
        final ZonedDateTime time = ZonedDateTime.of(2026, 8, 7, 10, 0,
                0, 0, ZoneId.of("America/Toronto"));
        final Review review = new Review("review-1", 550, "movie",
                "Fight Club", "elodie", "Elodie", 95.0,
                "Loved it.", time, time, "moviematch", new HashSet<>());

        review.like("lily");
        review.like("lily");
        review.like("yidan");
        review.unlike("lily");

        assertEquals(1, review.getLikeCount());
        assertFalse(review.getLikedByUsernames().contains("lily"));
    }
}
