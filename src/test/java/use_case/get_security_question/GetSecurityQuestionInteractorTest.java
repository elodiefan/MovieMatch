package use_case.get_security_question;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GetSecurityQuestionInteractorTest {

    @Test
    void switchUsesCurrentUsernameAndSecurityQuestion() {
        final GetSecurityQuestionUserDataAccessInterface dataAccess =
                new GetSecurityQuestionUserDataAccessInterface() {
                    @Override
                    public String getCurrentUsername() {
                        return "bob";
                    }

                    @Override
                    public String getSecurityQuestion() {
                        return "Favourite movie?";
                    }
                };
        final GetSecurityQuestionOutputData[] presented = new GetSecurityQuestionOutputData[1];

        new GetSecurityQuestionInteractor(dataAccess, data -> presented[0] = data)
                .switchToDeleteAccountView();

        assertEquals("bob", presented[0].getUsername());
        assertEquals("Favourite movie?", presented[0].getSecurityQuestion());
    }
}
