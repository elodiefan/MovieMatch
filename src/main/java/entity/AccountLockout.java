package entity;

/**
 * The lock-out rules for one account.
 * After {@link #MAX_ATTEMPTS} wrong security answers the account is locked for
 * {@link #LOCKOUT_MINUTES} minutes, and answers are refused until the lock
 * expires. Keeping these rules here rather than in an interactor means the
 * policy lives with the other business rules and can be unit-tested on its own.
 */
public class AccountLockout {

    /**
     * Number of wrong answers allowed before the account locks.
     */
    public static final int MAX_ATTEMPTS = 3;

    /**
     * How long the account stays locked after too many wrong answers.
     */
    public static final int LOCKOUT_MINUTES = 5;

    private static final long MILLIS_PER_MINUTE = 60_000L;
    private static final long MILLIS_PER_SECOND = 1000L;

    private int failedAttempts;

    /**
     * Epoch-millis until which the account is locked; 0 means not locked.
     */
    private long lockedUntil;

    /**
     * Records one wrong answer, locking the account if that was the last
     * allowed attempt.
     */
    public void recordFailedAttempt() {
        failedAttempts++;
        if (failedAttempts >= MAX_ATTEMPTS) {
            lockedUntil = System.currentTimeMillis() + LOCKOUT_MINUTES * MILLIS_PER_MINUTE;
            // The lock now governs access, so the counter starts fresh.
            failedAttempts = 0;
        }
    }

    /**
     * Clears everything after a correct answer.
     */
    public void reset() {
        failedAttempts = 0;
        lockedUntil = 0L;
    }

    /**
     * Returns whether the account is locked right now.
     * @return true if the account is locked right now. An expired lock is cleared here, so the next attempt is allowed.
     */
    public boolean isLockedOut() {
        boolean returnValue = lockedUntil != 0L;
        if (System.currentTimeMillis() >= lockedUntil) {
            lockedUntil = 0L;
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Returns how many attempts remain before the account locks.
     * @return how many attempts remain before the account locks
     */
    public int remainingAttempts() {
        return MAX_ATTEMPTS - failedAttempts;
    }

    /**
     * Returns seconds left on the current lock-out.
     * @return seconds left on the current lock-out, or 0 if not locked
     */
    public long remainingLockSeconds() {
        final long returnValue;
        if (lockedUntil == 0L) {
            returnValue = 0L;
        }
        else {
            returnValue = Math.max(0L, (lockedUntil - System.currentTimeMillis()) / MILLIS_PER_SECOND);
        }
        return returnValue;
    }
}
