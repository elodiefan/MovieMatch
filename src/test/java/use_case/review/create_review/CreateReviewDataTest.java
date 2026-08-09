package use_case.review.create_review;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

class CreateReviewDataTest {

    @Test
    void inputDataReturnsReviewInformation() {
        final CreateReviewInputData data = new CreateReviewInputData(
                101, "movie", "Example Movie", 2026, "/poster.jpg",
                "bob", "Bob", 8.5, "A good movie");

        assertEquals(101, data.getMediaId());
        assertEquals("movie", data.getMediaType());
        assertEquals("Example Movie", data.getMediaTitle());
        assertEquals(2026, data.getReleaseYear());
        assertEquals("/poster.jpg", data.getPosterPath());
        assertEquals("bob", data.getAuthorUsername());
        assertEquals("Bob", data.getAuthorDisplayName());
        assertEquals(8.5, data.getRating());
        assertEquals("A good movie", data.getReviewText());
    }

    @Test
    void outputDataReturnsCreatedReviewInformation() {
        final ZonedDateTime createdAt = ZonedDateTime.parse(
                "2026-08-09T10:00:00-04:00[America/Toronto]");
        final ZonedDateTime updatedAt = createdAt.plusMinutes(5);
        final CreateReviewOutputData data = new CreateReviewOutputData(
                "review-1", "bob", "Bob", 8.5, "A good movie",
                createdAt, updatedAt, 2, "user");

        assertEquals("review-1", data.getReviewId());
        assertEquals("bob", data.getAuthorUsername());
        assertEquals("Bob", data.getAuthorDisplayName());
        assertEquals(8.5, data.getRating());
        assertEquals("A good movie", data.getReviewText());
        assertEquals(createdAt, data.getCreatedAt());
        assertEquals(updatedAt, data.getUpdatedAt());
        assertEquals(2, data.getLikeCount());
        assertEquals("user", data.getSource());
    }
}
