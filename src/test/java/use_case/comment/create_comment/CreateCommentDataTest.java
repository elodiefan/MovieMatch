package use_case.comment.create_comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CreateCommentDataTest {

    @Test
    void inputDataReturnsCommentInformation() {
        final CreateCommentInputData data = new CreateCommentInputData(
                "review-1", "comment-1", "bob", "Bob", "A reply");

        assertEquals("review-1", data.getReviewId());
        assertEquals("comment-1", data.getParentCommentId());
        assertEquals("bob", data.getAuthorUsername());
        assertEquals("Bob", data.getAuthorDisplayName());
        assertEquals("A reply", data.getCommentText());
    }

    @Test
    void outputDataReturnsCreatedStatus() {
        assertTrue(new CreateCommentOutputData(true).isCreated());
    }
}
