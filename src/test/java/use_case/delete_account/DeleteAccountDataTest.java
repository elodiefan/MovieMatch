package use_case.delete_account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class DeleteAccountDataTest {

    @Test
    void inputDataReturnsAccountInformation() {
        final DeleteAccountInputData inputData = new DeleteAccountInputData(
                "bob", "Bob", "password", "Favourite movie?", "Example Movie");

        assertEquals("bob", inputData.getUsername());
        assertEquals("Bob", inputData.getDisplayName());
        assertEquals("password", inputData.getPassword());
        assertEquals("Favourite movie?", inputData.getSecurityQuestion());
        assertEquals("Example Movie", inputData.getSecurityAnswer());
    }

    @Test
    void outputDataReturnsUsernameAndStatus() {
        final DeleteAccountOutputData outputData = new DeleteAccountOutputData("bob", false);

        assertEquals("bob", outputData.getUsername());
        assertFalse(outputData.isUseCaseFailed());
    }
}
