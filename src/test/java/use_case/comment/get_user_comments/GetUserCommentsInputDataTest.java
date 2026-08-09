package use_case.comment.get_user_comments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetUserCommentsInputDataTest {

    @Test
    void inputDataReturnsUsername() {
        final GetUserCommentsInputData data = new GetUserCommentsInputData("bob");

        assertEquals("bob", data.getUsername());
    }
}
