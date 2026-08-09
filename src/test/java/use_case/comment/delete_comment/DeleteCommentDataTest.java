package use_case.comment.delete_comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DeleteCommentDataTest {

    @Test
    void inputDataReturnsCommentAndUsername() {
        final DeleteCommentInputData data = new DeleteCommentInputData("comment-1", "bob");

        assertEquals("comment-1", data.getCommentId());
        assertEquals("bob", data.getUsername());
    }

    @Test
    void outputDataReturnsDeletedStatus() {
        assertTrue(new DeleteCommentOutputData(true).isDeleted());
    }
}
