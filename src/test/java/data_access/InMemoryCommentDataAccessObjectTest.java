package data_access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import entity.Comment;
import org.junit.jupiter.api.Test;

/**
 * Tests for the in-memory comment data access object.
 */
public class InMemoryCommentDataAccessObjectTest {

    private static final ZonedDateTime TIME = ZonedDateTime.of(2026, 8, 7,
            10, 0, 0, 0, ZoneId.of("America/Toronto"));

    @Test
    void saveAndGetCommentById() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final Comment comment = makeComment("comment-1", "review-1", null,
                "elodie", TIME);

        dao.saveComment(comment);

        assertTrue(dao.existsByCommentId("comment-1"));
        assertEquals(comment, dao.getCommentById("comment-1").get());
        assertFalse(dao.getCommentById("missing").isPresent());
    }

    @Test
    void getCommentsByReviewIdFiltersComments() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        dao.saveComment(makeComment("comment-1", "review-1", null,
                "elodie", TIME));
        dao.saveComment(makeComment("comment-2", "review-2", null,
                "lily", TIME));
        dao.saveComment(makeComment("comment-3", "review-1", null,
                "yidan", TIME));

        final List<Comment> comments = dao.getCommentsByReviewId("review-1");

        assertEquals(2, comments.size());
        assertEquals("comment-1", comments.get(0).getCommentId());
        assertEquals("comment-3", comments.get(1).getCommentId());
    }

    @Test
    void getCommentsByUsernameFiltersComments() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        dao.saveComment(makeComment("comment-1", "review-1", null,
                "elodie", TIME));
        dao.saveComment(makeComment("comment-2", "review-2", null,
                "lily", TIME));
        dao.saveComment(makeComment("comment-3", "review-3", null,
                "elodie", TIME));

        final List<Comment> comments = dao.getCommentsByUsername("elodie");

        assertEquals(2, comments.size());
        assertEquals("comment-1", comments.get(0).getCommentId());
        assertEquals("comment-3", comments.get(1).getCommentId());
    }

    @Test
    void getRepliesByParentCommentIdFiltersReplies() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        dao.saveComment(makeComment("comment-1", "review-1", null,
                "elodie", TIME));
        dao.saveComment(makeComment("comment-2", "review-1", "comment-1",
                "lily", TIME));
        dao.saveComment(makeComment("comment-3", "review-1", "comment-1",
                "yidan", TIME));
        dao.saveComment(makeComment("comment-4", "review-1", "comment-2",
                "enzo", TIME));

        final List<Comment> replies =
                dao.getRepliesByParentCommentId("comment-1");

        assertEquals(2, replies.size());
        assertEquals("comment-2", replies.get(0).getCommentId());
        assertEquals("comment-3", replies.get(1).getCommentId());
    }

    @Test
    void editDeleteLikeAndUnlikeComment() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();
        final Comment comment = makeComment("comment-1", "review-1", null,
                "elodie", TIME);
        dao.saveComment(comment);

        assertTrue(dao.editComment("comment-1", "Updated."));
        assertEquals("Updated.", comment.getCommentText());
        assertTrue(dao.likeComment("comment-1", "lily"));
        assertEquals(1, comment.getLikeCount());
        assertTrue(dao.unlikeComment("comment-1", "lily"));
        assertEquals(0, comment.getLikeCount());
        assertTrue(dao.deleteComment("comment-1"));
        assertFalse(dao.existsByCommentId("comment-1"));
    }

    @Test
    void operationsReturnFalseForMissingComment() {
        final InMemoryCommentDataAccessObject dao =
                new InMemoryCommentDataAccessObject();

        assertFalse(dao.editComment("missing", "Updated."));
        assertFalse(dao.deleteComment("missing"));
        assertFalse(dao.likeComment("missing", "lily"));
        assertFalse(dao.unlikeComment("missing", "lily"));
    }

    private Comment makeComment(String commentId, String reviewId,
                                String parentCommentId, String username,
                                ZonedDateTime createdAt) {
        return new Comment(commentId, reviewId, parentCommentId, username,
                username, "I agree.", createdAt, new HashSet<>());
    }
}
