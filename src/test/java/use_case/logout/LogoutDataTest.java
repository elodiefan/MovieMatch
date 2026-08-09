package use_case.logout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class LogoutDataTest {

    @Test
    void inputDataReturnsUsername() {
        final LogoutInputData inputData = new LogoutInputData("bob");

        assertEquals("bob", inputData.getUsername());
    }

    @Test
    void outputDataReturnsUsernameAndStatus() {
        final LogoutOutputData outputData = new LogoutOutputData("bob", false);

        assertEquals("bob", outputData.getUsername());
        assertFalse(outputData.isUseCaseFailed());
    }
}
