package use_case.signup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SignupDataTest {

    @Test
    void inputDataReturnsSignupInformation() {
        final SignupInputData data = new SignupInputData(
                "bob", "Bob", "password", "password",
                "Favourite movie?", "Example Movie");

        assertEquals("bob", data.getUsername());
        assertEquals("Bob", data.getDisplayName());
        assertEquals("password", data.getPassword());
        assertEquals("password", data.getRepeatPassword());
        assertEquals("Favourite movie?", data.getSecurityQuestion());
        assertEquals("Example Movie", data.getSecurityAnswer());
    }

    @Test
    void outputDataReturnsCreatedUserInformation() {
        final SignupOutputData data = new SignupOutputData("bob", "Bob");

        assertEquals("bob", data.getUsername());
        assertEquals("Bob", data.getDisplayName());
    }
}
