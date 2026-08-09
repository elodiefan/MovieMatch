package use_case.login;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LoginDataTest {

    @Test
    void inputDataReturnsLoginInformation() {
        final LoginInputData data = new LoginInputData("bob", "password");

        assertEquals("bob", data.getUsername());
        assertEquals("password", data.getPassword());
    }

    @Test
    void outputDataReturnsUserInformation() {
        final LoginOutputData data = new LoginOutputData("bob", "Bob", false);

        assertEquals("bob", data.getUsername());
        assertEquals("Bob", data.getDisplayName());
    }
}
