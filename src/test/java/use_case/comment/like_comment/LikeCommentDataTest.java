package use_case.comment.like_comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LikeCommentDataTest {

    @Test
    void inputDataReturnsCommentAndUsername() {
        final LikeCommentInputData data = new LikeCommentInputData("comment-1", "bob");

        assertEquals("comment-1", data.getCommentId());
        assertEquals("bob", data.getUsername());
    }

    @Test
    void outputDataReturnsLikedStatus() {
        assertTrue(new LikeCommentOutputData(true).isLiked());
    }
}
