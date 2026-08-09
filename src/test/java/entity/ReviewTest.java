package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Review entity.
 */
public class ReviewTest {

    @Test
    void constructorStoresReviewInformation() {
        final ZonedDateTime createdAt = ZonedDateTime.parse("2026-08-08T10:00:00-04:00[America/Toronto]");
        final ZonedDateTime updatedAt = ZonedDateTime.parse("2026-08-08T11:00:00-04:00[America/Toronto]");
        final Review review = new Review("review-1", 101, "movie", "Example Movie",
                "bob", "Bob", 8.5, "A good movie", createdAt, updatedAt,
                "user", Set.of("alice"));

        assertEquals("review-1", review.getReviewId());
        assertEquals(101, review.getMediaId());
        assertEquals("movie", review.getMediaType());
        assertEquals("Example Movie", review.getMediaTitle());
        assertEquals(0, review.getReleaseYear());
        assertEquals("", review.getPosterPath());
        assertEquals("bob", review.getAuthorUsername());
        assertEquals("Bob", review.getAuthorDisplayName());
        assertEquals(8.5, review.getRating());
        assertEquals("A good movie", review.getReviewText());
        assertEquals(createdAt, review.getCreatedAt());
        assertEquals(updatedAt, review.getUpdatedAt());
        assertEquals("user", review.getSource());
        assertEquals(Set.of("alice"), review.getLikedByUsernames());
    }

    @Test
    void constructorWithDisplayMetadataStoresExtraInformation() {
        final ZonedDateTime time = ZonedDateTime.parse("2026-08-08T10:00:00-04:00[America/Toronto]");
        final Review review = new Review("review-2", 202, "tv", "Example Show",
                2024, "/poster.jpg", "bob", "Bob", 7.0, "Worth watching",
                time, time, "tmdb", Set.of());

        assertEquals(2024, review.getReleaseYear());
        assertEquals("/poster.jpg", review.getPosterPath());
    }

    @Test
    void editChangesOnlyEditableReviewInformation() {
        final ZonedDateTime createdAt = ZonedDateTime.parse("2026-08-08T10:00:00-04:00[America/Toronto]");
        final ZonedDateTime originalUpdatedAt = ZonedDateTime.parse("2026-08-08T11:00:00-04:00[America/Toronto]");
        final ZonedDateTime newUpdatedAt = ZonedDateTime.parse("2026-08-08T12:00:00-04:00[America/Toronto]");
        final Review review = new Review("review-3", 303, "movie", "Example Movie",
                "bob", "Bob", 6.0, "Original text", createdAt, originalUpdatedAt,
                "user", Set.of());

        review.edit(9.0, "Updated text", newUpdatedAt);

        assertEquals(9.0, review.getRating());
        assertEquals("Updated text", review.getReviewText());
        assertEquals(newUpdatedAt, review.getUpdatedAt());
        assertEquals("review-3", review.getReviewId());
        assertEquals(createdAt, review.getCreatedAt());
    }
}
