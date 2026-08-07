package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Comment entity.
 */
public class CommentTest {

    @Test
    void constructorStoresCommentFields() {
        final ZonedDateTime createdAt = ZonedDateTime.of(2026, 8, 7, 10, 0,
                0, 0, ZoneId.of("America/Toronto"));
        final Set<String> likedBy = new HashSet<>();
        likedBy.add("lily");

        final Comment comment = new Comment("comment-1", "review-1",
                "parent-1", "elodie", "Elodie", "I agree.", createdAt,
                likedBy);

        assertEquals("comment-1", comment.getCommentId());
        assertEquals("comment-1", comment.getContentId());
        assertEquals("review-1", comment.getReviewId());
        assertEquals("parent-1", comment.getParentCommentId());
        assertEquals("elodie", comment.getAuthorUsername());
        assertEquals("Elodie", comment.getAuthorDisplayName());
        assertEquals("I agree.", comment.getCommentText());
        assertEquals(createdAt, comment.getCreatedAt());
        assertEquals(1, comment.getLikeCount());
    }

    @Test
    void topLevelCommentCanHaveNullParentCommentId() {
        final ZonedDateTime createdAt = ZonedDateTime.of(2026, 8, 7, 10, 0,
                0, 0, ZoneId.of("America/Toronto"));

        final Comment comment = new Comment("comment-1", "review-1", null,
                "elodie", "Elodie", "I agree.", createdAt, new HashSet<>());

        assertNull(comment.getParentCommentId());
    }

    @Test
    void editUpdatesCommentTextOnly() {
        final ZonedDateTime createdAt = ZonedDateTime.of(2026, 8, 7, 10, 0,
                0, 0, ZoneId.of("America/Toronto"));
        final Comment comment = new Comment("comment-1", "review-1", null,
                "elodie", "Elodie", "I agree.", createdAt, new HashSet<>());

        comment.edit("Actually, I changed my mind.");

        assertEquals("comment-1", comment.getCommentId());
        assertEquals("review-1", comment.getReviewId());
        assertEquals("elodie", comment.getAuthorUsername());
        assertEquals("Actually, I changed my mind.",
                comment.getCommentText());
    }

    @Test
    void likesCanBeAddedAndRemoved() {
        final ZonedDateTime createdAt = ZonedDateTime.of(2026, 8, 7, 10, 0,
                0, 0, ZoneId.of("America/Toronto"));
        final Comment comment = new Comment("comment-1", "review-1", null,
                "elodie", "Elodie", "I agree.", createdAt, new HashSet<>());

        comment.like("lily");
        comment.like("lily");
        comment.like("yidan");
        comment.unlike("lily");

        assertEquals(1, comment.getLikeCount());
        assertFalse(comment.getLikedByUsernames().contains("lily"));
    }
}
