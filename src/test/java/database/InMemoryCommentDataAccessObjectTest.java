package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import entity.Comment;

class InMemoryCommentDataAccessObjectTest {

    private static final ZonedDateTime CREATED = ZonedDateTime.of(
            2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    @Test
    void saveFindAndFilterComments() {
        final InMemoryCommentDataAccessObject dataAccess =
                new InMemoryCommentDataAccessObject();
        final Comment parent = comment("c1", "r1", null, "alice");
        final Comment reply = comment("c2", "r1", "c1", "bob");
        final Comment other = comment("c3", "r2", null, "alice");
        dataAccess.saveComment(parent);
        dataAccess.saveComment(reply);
        dataAccess.saveComment(other);

        assertTrue(dataAccess.existsByCommentId("c1"));
        assertFalse(dataAccess.existsByCommentId("missing"));
        assertEquals(parent, dataAccess.getCommentById("c1").orElseThrow());
        assertTrue(dataAccess.getCommentById("missing").isEmpty());
        assertEquals(List.of(parent, reply),
                dataAccess.getCommentsByReviewId("r1"));
        assertEquals(List.of(parent, other),
                dataAccess.getCommentsByUsername("alice"));
        assertEquals(List.of(reply),
                dataAccess.getRepliesByParentCommentId("c1"));
        assertTrue(dataAccess.getRepliesByParentCommentId("missing").isEmpty());
        assertEquals(List.of(parent, reply, other), dataAccess.getAllComments());
    }

    @Test
    void mutationsCoverExistingAndMissingComments() {
        final InMemoryCommentDataAccessObject dataAccess =
                new InMemoryCommentDataAccessObject();
        final Comment comment = comment("c1", "r1", null, "alice");
        dataAccess.saveComment(comment);

        assertTrue(dataAccess.editComment("c1", "updated"));
        assertEquals("updated", comment.getCommentText());
        assertFalse(dataAccess.editComment("missing", "text"));
        assertTrue(dataAccess.likeComment("c1", "bob"));
        assertTrue(comment.getLikedByUsernames().contains("bob"));
        assertFalse(dataAccess.likeComment("missing", "bob"));
        assertTrue(dataAccess.unlikeComment("c1", "bob"));
        assertFalse(comment.getLikedByUsernames().contains("bob"));
        assertFalse(dataAccess.unlikeComment("missing", "bob"));
        assertFalse(dataAccess.deleteComment("missing"));
        assertTrue(dataAccess.deleteComment("c1"));
        assertFalse(dataAccess.existsByCommentId("c1"));
        dataAccess.close();
    }

    private static Comment comment(String id, String reviewId,
                                   String parentId, String username) {
        return new Comment(id, reviewId, parentId, username, "Display",
                "Text", CREATED, Set.of());
    }
}
