package use_case.security_question;

import entity.AccountLockout;
import entity.User;

/**
 * Interactor (business logic) for changing a password via a security question.
 * <p>
 * Responsibilities, matching the feature spec:
 * <ul>
 *     <li>show the user's security question;</li>
 *     <li>verify the typed answer;</li>
 *     <li>let the user keep trying while wrong;</li>
 *     <li>lock the account once too many attempts fail.</li>
 * </ul>
 * <p>
 * The counting and timing rules live in {@link AccountLockout}, and the records
 * themselves are kept by a {@link LockoutTracker}. This class holds no state of
 * its own: it only decides which rule to apply and what to report back.
 */
public class SecurityQuestionInteractor implements SecurityQuestionInputBoundary {

    private final SecurityQuestionUserDataAccessInterface userDataAccessObject;
    private final SecurityQuestionOutputBoundary presenter;
    private final LockoutTracker lockoutTracker;

    public SecurityQuestionInteractor(SecurityQuestionUserDataAccessInterface userDataAccessObject,
                                      SecurityQuestionOutputBoundary presenter,
                                      LockoutTracker lockoutTracker) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
        this.lockoutTracker = lockoutTracker;
    }

    @Override
    public void loadSecurityQuestion(SecurityQuestionInputData inputData) {
        final String username = inputData.getUsername();

        // Can't show a question for an account that doesn't exist.
        if (!userDataAccessObject.existsByName(username)) {
            presenter.prepareFailView(new SecurityQuestionOutputData(
                    username, "", true, lockoutFor(username).remainingAttempts(), false, 0L));
            return;
        }

        final User user = userDataAccessObject.get(username);
        presenter.presentSecurityQuestion(new SecurityQuestionOutputData(
                username, user.getSecurityQuestion(), false,
                lockoutFor(username).remainingAttempts(), false, 0L));
    }

    @Override
    public void verifyAnswer(SecurityQuestionInputData inputData) {
        final String username = inputData.getUsername();

        // 1. No such account -> fail.
        if (!userDataAccessObject.existsByName(username)) {
            presenter.prepareFailView(new SecurityQuestionOutputData(
                    username, "", true, AccountLockout.MAX_ATTEMPTS, false, 0L));
            return;
        }

        final User user = userDataAccessObject.get(username);
        final String question = user.getSecurityQuestion();
        final AccountLockout lockout = lockoutFor(username);

        // 2. Currently locked out -> reject without even checking the answer.
        if (lockout.isLockedOut()) {
            presenter.prepareFailView(new SecurityQuestionOutputData(
                    username, question, true, 0, true, lockout.remainingLockSeconds()));
            return;
        }

        // 3. Correct answer -> clear the record and let them change their password.
        //    Compared case-insensitively and trimmed, so "Fido " matches "fido".
        if (matches(user.getSecurityAnswer(), inputData.getSecurityAnswer())) {
            lockout.reset();
            presenter.prepareSuccessView(new SecurityQuestionOutputData(
                    username, question, false, AccountLockout.MAX_ATTEMPTS, false, 0L));
            return;
        }

        // 4. Wrong answer -> let the entity apply the counting and locking rules.
        lockout.recordFailedAttempt();

        if (lockout.isLockedOut()) {
            presenter.prepareFailView(new SecurityQuestionOutputData(
                    username, question, true, 0, true, lockout.remainingLockSeconds()));
        }
        else {
            presenter.prepareFailView(new SecurityQuestionOutputData(
                    username, question, true, lockout.remainingAttempts(), false, 0L));
        }
    }

    /** @return true if the answers match, ignoring case and surrounding spaces. */
    private boolean matches(String expected, String actual) {
        return expected != null && actual != null && expected.trim().equalsIgnoreCase(actual.trim());
    }

    /** @return this account's lock-out record. */
    private AccountLockout lockoutFor(String username) {
        return lockoutTracker.forUser(username);
    }
}
