package use_case.security_question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class SecurityQuestionDataTest {

    @Test
    void inputDataReturnsRecoveryInformation() {
        final SecurityQuestionInputData data = new SecurityQuestionInputData(
                "bob", "Example Movie");

        assertEquals("bob", data.getUsername());
        assertEquals("Example Movie", data.getSecurityAnswer());
    }

    @Test
    void outputDataReturnsRecoveryStatus() {
        final SecurityQuestionOutputData data = new SecurityQuestionOutputData(
                "bob", "Favourite movie?", false, 3, false, 0);

        assertEquals("bob", data.getUsername());
        assertEquals("Favourite movie?", data.getSecurityQuestion());
        assertFalse(data.isUseCaseFailed());
        assertEquals(3, data.getRemainingAttempts());
        assertFalse(data.isLockedOut());
        assertEquals(0, data.getLockRemainingSeconds());
    }
}
