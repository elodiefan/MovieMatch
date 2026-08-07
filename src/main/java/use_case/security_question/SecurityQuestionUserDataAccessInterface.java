package use_case.security_question;

import entity.User;

/** Data-access interface for the Security Question use case. */
public interface SecurityQuestionUserDataAccessInterface {

    /** Checks whether an account with the given username exists. */
    boolean existsByName(String username);

    /** Returns the user with the given username. */
    User get(String username);
}
