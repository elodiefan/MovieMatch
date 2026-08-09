package use_case.review.edit_review;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

class EditReviewDataTest {

    @Test
    void inputDataReturnsEditedInformation() {
        final EditReviewInputData data = new EditReviewInputData(
                "review-1", "bob", 9.0, "Updated review");

        assertEquals("review-1", data.getReviewId());
        assertEquals("bob", data.getUsername());
        assertEquals(9.0, data.getRating());
        assertEquals("Updated review", data.getReviewText());
    }

    @Test
    void outputDataReturnsEditedReviewInformation() {
        final ZonedDateTime createdAt = ZonedDateTime.parse(
                "2026-08-09T10:00:00-04:00[America/Toronto]");
        final ZonedDateTime updatedAt = createdAt.plusMinutes(5);
        final EditReviewOutputData data = new EditReviewOutputData(
                "review-1", "bob", "Bob", 9.0, "Updated review",
                createdAt, updatedAt, 2, "user");

        assertEquals("review-1", data.getReviewId());
        assertEquals("bob", data.getAuthorUsername());
        assertEquals("Bob", data.getAuthorDisplayName());
        assertEquals(9.0, data.getRating());
        assertEquals("Updated review", data.getReviewText());
        assertEquals(createdAt, data.getCreatedAt());
        assertEquals(updatedAt, data.getUpdatedAt());
        assertEquals(2, data.getLikeCount());
        assertEquals("user", data.getSource());
    }
}
