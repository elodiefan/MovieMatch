package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AccountLockoutTest {

    @Test
    void failedAttemptsCountDownToLockout() {
        final AccountLockout lockout = new AccountLockout();

        assertEquals(AccountLockout.MAX_ATTEMPTS, lockout.remainingAttempts());
        lockout.recordFailedAttempt();
        assertEquals(AccountLockout.MAX_ATTEMPTS - 1, lockout.remainingAttempts());
        assertFalse(lockout.isLockedOut());

        lockout.recordFailedAttempt();
        lockout.recordFailedAttempt();
        assertTrue(lockout.isLockedOut());
        assertTrue(lockout.remainingLockSeconds() > 0);
    }

    @Test
    void resetClearsFailuresAndLockout() {
        final AccountLockout lockout = new AccountLockout();
        for (int i = 0; i < AccountLockout.MAX_ATTEMPTS; i++) {
            lockout.recordFailedAttempt();
        }
        assertTrue(lockout.isLockedOut());

        lockout.reset();

        assertFalse(lockout.isLockedOut());
        assertEquals(AccountLockout.MAX_ATTEMPTS, lockout.remainingAttempts());
        assertEquals(0L, lockout.remainingLockSeconds());
    }
}
