package use_case.get_security_question;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetSecurityQuestionDataTest {

    @Test
    void outputDataReturnsUsernameAndQuestion() {
        final GetSecurityQuestionOutputData data = new GetSecurityQuestionOutputData(
                "bob", "Favourite movie?");

        assertEquals("bob", data.getUsername());
        assertEquals("Favourite movie?", data.getSecurityQuestion());
    }
}
