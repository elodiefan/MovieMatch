package use_case.reset_password;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class ResetPasswordDataTest {

    @Test
    void inputDataReturnsPasswordInformation() {
        final ResetPasswordInputData data = new ResetPasswordInputData(
                "bob", "new-password", "new-password");

        assertEquals("bob", data.getUsername());
        assertEquals("new-password", data.getNewPassword());
        assertEquals("new-password", data.getConfirmPassword());
    }

    @Test
    void outputDataReturnsUsernameAndStatus() {
        final ResetPasswordOutputData data = new ResetPasswordOutputData("bob", false);

        assertEquals("bob", data.getUsername());
        assertFalse(data.isUseCaseFailed());
    }
}
