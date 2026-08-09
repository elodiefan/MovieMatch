package use_case.change_username;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ChangeUsernameDataTest {

    @Test
    void inputDataReturnsUserInformation() {
        final ChangeUsernameInputData inputData = new ChangeUsernameInputData(
                "bob", "bob_new", "Bob");

        assertEquals("bob", inputData.getUsername());
        assertEquals("bob_new", inputData.getNewUsername());
        assertEquals("Bob", inputData.getDisplayName());
    }

    @Test
    void outputDataReturnsUserInformation() {
        final ChangeUsernameOutputData outputData = new ChangeUsernameOutputData(
                "bob", "bob_new", "Bob");

        assertEquals("bob", outputData.getUsername());
        assertEquals("bob_new", outputData.getNewUsername());
        assertEquals("Bob", outputData.getDisplayName());
    }
}
