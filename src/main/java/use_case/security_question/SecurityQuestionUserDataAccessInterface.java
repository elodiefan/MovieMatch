package use_case.security_question;

import entity.User;

/**
 * Data-access interface for the Security Question use case.
 * <p>
 * The interactor depends on this abstraction (not a concrete database), so any
 * DAO â€” an in-memory map for tests, or the real {@code InternalDatabase} â€” can
 * be plugged in. Only read access is needed: this use case verifies identity;
 * it does not itself write the new password (that is the change-password use
 * case, opened once the answer is correct).
 */
public interface SecurityQuestionUserDataAccessInterface {

    /**
     * Checks whether an account with the given username exists.
     * @param username the username to look up
     * @return true if the account exists
     */
    boolean existsByName(String username);

    /**
     * Returns the user with the given username.
     * Callers should only call this after {@link #existsByName} returns true.
     * @param username the username to look up
     * @return the matching user
     */
    User get(String username);
}
