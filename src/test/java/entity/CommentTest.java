package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Comment entity.
 */
public class CommentTest {

    @Test
    void constructorStoresTopLevelCommentInformation() {
        final ZonedDateTime createdAt = ZonedDateTime.parse("2026-08-08T10:00:00-04:00[America/Toronto]");
        final Comment comment = new Comment("comment-1", "review-1", null,
                "bob", "Bob", "I agree", createdAt, Set.of("alice"));

        assertEquals("comment-1", comment.getCommentId());
        assertEquals("review-1", comment.getReviewId());
        assertNull(comment.getParentCommentId());
        assertEquals("bob", comment.getAuthorUsername());
        assertEquals("Bob", comment.getAuthorDisplayName());
        assertEquals("I agree", comment.getCommentText());
        assertEquals(createdAt, comment.getCreatedAt());
        assertEquals(Set.of("alice"), comment.getLikedByUsernames());
    }

    @Test
    void constructorStoresParentIdForReply() {
        final ZonedDateTime createdAt = ZonedDateTime.parse("2026-08-08T10:00:00-04:00[America/Toronto]");
        final Comment reply = new Comment("comment-2", "review-1", "comment-1",
                "bob", "Bob", "This is a reply", createdAt, Set.of());

        assertEquals("comment-1", reply.getParentCommentId());
    }

    @Test
    void editChangesCommentText() {
        final ZonedDateTime createdAt = ZonedDateTime.parse("2026-08-08T10:00:00-04:00[America/Toronto]");
        final Comment comment = new Comment("comment-3", "review-1", null,
                "bob", "Bob", "Original text", createdAt, Set.of());

        comment.edit("Updated text");

        assertEquals("Updated text", comment.getCommentText());
        assertEquals("comment-3", comment.getCommentId());
        assertEquals(createdAt, comment.getCreatedAt());
    }
}
