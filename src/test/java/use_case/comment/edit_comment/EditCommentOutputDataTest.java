package use_case.comment.edit_comment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EditCommentOutputDataTest {

    @Test
    void reportsWhetherCommentWasEdited() {
        assertTrue(new EditCommentOutputData(true).isEdited());
        assertFalse(new EditCommentOutputData(false).isEdited());
    }
}
