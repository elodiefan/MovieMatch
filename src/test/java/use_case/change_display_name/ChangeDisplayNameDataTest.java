package use_case.change_display_name;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ChangeDisplayNameDataTest {

    @Test
    void inputDataReturnsNames() {
        final ChangeDisplayNameInputData inputData = new ChangeDisplayNameInputData(
                "bob", "Bob", "Bobby");

        assertEquals("bob", inputData.getUsername());
        assertEquals("Bob", inputData.getOldDisplayName());
        assertEquals("Bobby", inputData.getNewDisplayName());
    }

    @Test
    void outputDataReturnsNames() {
        final ChangeDisplayNameOutputData outputData = new ChangeDisplayNameOutputData(
                "bob", "Bob", "Bobby");

        assertEquals("bob", outputData.getUsername());
        assertEquals("Bob", outputData.getOldDisplayName());
        assertEquals("Bobby", outputData.getNewDisplayName());
    }
}
