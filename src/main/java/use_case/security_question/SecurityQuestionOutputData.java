package use_case.security_question;

/**
 * Output data for the Security Question use case.
 * One immutable object describes every outcome the presenter/view might need
 * to render: the question to show, whether the step failed, how many tries are
 * left, and â€” if the account is locked â€” how long until it unlocks.
 */
public class SecurityQuestionOutputData {

    private final String username;
    private final String securityQuestion;
    private final boolean useCaseFailed;
    private final int remainingAttempts;
    private final boolean lockedOut;
    private final long lockRemainingSeconds;

    public SecurityQuestionOutputData(String username, String securityQuestion, boolean useCaseFailed,
                                      int remainingAttempts, boolean lockedOut, long lockRemainingSeconds) {
        this.username = username;
        this.securityQuestion = securityQuestion;
        this.useCaseFailed = useCaseFailed;
        this.remainingAttempts = remainingAttempts;
        this.lockedOut = lockedOut;
        this.lockRemainingSeconds = lockRemainingSeconds;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's security question.
     * @return the user's security question, or "" when unknown (e.g. no such account).
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Returns whether this step failed.
     * @return true if this step did not succeed (wrong answer, locked out, or no account).
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    /**
     * Returns how many attempts remain before lock-out.
     * @return how many attempts remain before lock-out.
     */
    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    /**
     * Returns whether the account is currently locked.
     * @return true if the account is currently locked.
     */
    public boolean isLockedOut() {
        return lockedOut;
    }

    /**
     * Returns seconds left on the current lock-out.
     * @return seconds left on the current lock-out (0 if not locked).
     */
    public long getLockRemainingSeconds() {
        return lockRemainingSeconds;
    }
}
