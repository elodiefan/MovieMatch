package use_case.security_question;

import entity.AccountLockout;

/** Keeps track of each account's lock-out record. */
public interface LockoutTracker {

    /** Returns the lock-out record for an account, creating an empty one the first time an account is seen. */
    AccountLockout forUser(String username);
}
