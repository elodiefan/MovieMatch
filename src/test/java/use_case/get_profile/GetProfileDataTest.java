package use_case.get_profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class GetProfileDataTest {

    @Test
    void inputDataReturnsProfileInformation() {
        final GetProfileInputData inputData = new GetProfileInputData("bob", "Bob");

        assertEquals("bob", inputData.getUsername());
        assertEquals("Bob", inputData.getDisplayName());
    }

    @Test
    void outputDataReturnsProfileInformation() {
        final GetProfileOutputData outputData = new GetProfileOutputData(
                "bob", "Bob", false, false);

        assertEquals("bob", outputData.getUsername());
        assertEquals("Bob", outputData.getDisplayName());
        assertFalse(outputData.isBlocked());
        assertFalse(outputData.isUseCaseFailed());
    }
}
