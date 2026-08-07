package use_case.get_security_question;

/** DAO for the Account Use Case. */
public interface GetSecurityQuestionUserDataAccessInterface {

    /** Returns the username of the current user. */
    String getCurrentUsername();

    /** Gets the security question of the current user. */
    String getSecurityQuestion();
}
