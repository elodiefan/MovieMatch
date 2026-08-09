package use_case.comment.unlike_comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UnlikeCommentDataTest {

    @Test
    void inputDataReturnsCommentAndUsername() {
        final UnlikeCommentInputData data = new UnlikeCommentInputData("comment-1", "bob");

        assertEquals("comment-1", data.getCommentId());
        assertEquals("bob", data.getUsername());
    }

    @Test
    void outputDataReturnsUnlikedStatus() {
        assertTrue(new UnlikeCommentOutputData(true).isUnliked());
    }
}
