package use_case.logout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class LogoutInteractorTest {

    @Test
    void executeClearsCurrentUserAndPresentsSuccess() {
        final String[] currentUsername = {"bob"};
        final LogoutUserDataAccessInterface dataAccess = new LogoutUserDataAccessInterface() {
            @Override
            public String getCurrentUsername() {
                return currentUsername[0];
            }

            @Override
            public void setCurrentUsername(String username) {
                currentUsername[0] = username;
            }
        };
        final LogoutOutputData[] presented = new LogoutOutputData[1];
        final LogoutOutputBoundary presenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                presented[0] = outputData;
            }

            @Override
            public void prepareFailView(String errorMessage) {
            }
        };

        new LogoutInteractor(dataAccess, presenter).execute(new LogoutInputData("bob"));

        assertNull(currentUsername[0]);
        assertEquals("bob", presented[0].getUsername());
        assertFalse(presented[0].isUseCaseFailed());
    }
}
