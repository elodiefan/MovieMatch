package use_case.security_question;

import entity.AccountLockout;

/**
 * Keeps track of each account's lock-out record.
 *
 * The interactor asks this for an account's record rather than storing the
 * records itself, so where they are kept is a detail of the outer layer. The
 * shipped implementation holds them in memory, which means lock-outs reset when
 * the app restarts; a database-backed implementation could replace it without
 * changing any use case code.
 */
public interface LockoutTracker {

    /**
     * Returns the lock-out record for an account, creating an empty one the
     * first time an account is seen.
     */
    AccountLockout forUser(String username);
}
