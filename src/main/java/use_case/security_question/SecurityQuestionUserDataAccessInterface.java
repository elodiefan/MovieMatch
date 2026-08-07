package use_case.security_question;

import entity.User;

/**
 * Data-access interface for the Security Question use case.
 *
 * The interactor depends on this abstraction (not a concrete database), so any
 * DAO — an in-memory map for tests, or the real InternalDatabase — can
 * be plugged in. Only read access is needed: this use case verifies identity;
 * it does not itself write the new password (that is the change-password use
 * case, opened once the answer is correct).
 */
public interface SecurityQuestionUserDataAccessInterface {

    /**
     * Checks whether an account with the given username exists.
     */
    boolean existsByName(String username);

    /**
     * Returns the user with the given username.
     * Callers should only call this after #existsByName returns true.
     */
    User get(String username);
}
