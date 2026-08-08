package use_case.security_question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import entity.AccountLockout;
import entity.StandardUser;
import entity.User;

class SecurityQuestionInteractorTest {

    private static final class FakeDataAccess implements SecurityQuestionUserDataAccessInterface {
        private User user;

        @Override
        public boolean existsByName(String username) {
            return user != null && user.getUsername().equals(username);
        }

        @Override
        public User get(String username) {
            return user;
        }
    }

    private static final class InMemoryTracker implements LockoutTracker {
        private final Map<String, AccountLockout> records = new HashMap<>();

        @Override
        public AccountLockout forUser(String username) {
            return records.computeIfAbsent(username, key -> new AccountLockout());
        }
    }

    private static final class RecordingPresenter implements SecurityQuestionOutputBoundary {
        private SecurityQuestionOutputData question;
        private SecurityQuestionOutputData success;
        private SecurityQuestionOutputData failure;

        @Override
        public void presentSecurityQuestion(SecurityQuestionOutputData outputData) {
            question = outputData;
        }

        @Override
        public void prepareSuccessView(SecurityQuestionOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(SecurityQuestionOutputData outputData) {
            failure = outputData;
        }
    }

    @Test
    void existingAccountLoadsItsQuestion() {
        final FakeDataAccess dataAccess = userDataAccess();
        final RecordingPresenter presenter = new RecordingPresenter();

        new SecurityQuestionInteractor(dataAccess, presenter, new InMemoryTracker())
                .loadSecurityQuestion(new SecurityQuestionInputData("yidan", ""));

        assertEquals("First pet?", presenter.question.getSecurityQuestion());
        assertFalse(presenter.question.isUseCaseFailed());
        assertEquals(AccountLockout.MAX_ATTEMPTS, presenter.question.getRemainingAttempts());
        assertNull(presenter.failure);
    }

    @Test
    void missingAccountFailsForLoadAndVerification() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        final RecordingPresenter loadPresenter = new RecordingPresenter();
        final InMemoryTracker tracker = new InMemoryTracker();

        new SecurityQuestionInteractor(dataAccess, loadPresenter, tracker)
                .loadSecurityQuestion(new SecurityQuestionInputData("missing", ""));
        assertTrue(loadPresenter.failure.isUseCaseFailed());

        final RecordingPresenter verifyPresenter = new RecordingPresenter();
        new SecurityQuestionInteractor(dataAccess, verifyPresenter, tracker)
                .verifyAnswer(new SecurityQuestionInputData("missing", "answer"));
        assertTrue(verifyPresenter.failure.isUseCaseFailed());
        assertEquals(AccountLockout.MAX_ATTEMPTS, verifyPresenter.failure.getRemainingAttempts());
    }

    @Test
    void correctAnswerIgnoresCaseAndWhitespaceAndResetsFailures() {
        final FakeDataAccess dataAccess = userDataAccess();
        final InMemoryTracker tracker = new InMemoryTracker();
        tracker.forUser("yidan").recordFailedAttempt();
        final RecordingPresenter presenter = new RecordingPresenter();

        new SecurityQuestionInteractor(dataAccess, presenter, tracker)
                .verifyAnswer(new SecurityQuestionInputData("yidan", "  MOCHI "));

        assertNull(presenter.failure);
        assertFalse(presenter.success.isUseCaseFailed());
        assertEquals(AccountLockout.MAX_ATTEMPTS, tracker.forUser("yidan").remainingAttempts());
    }

    @Test
    void wrongAnswersReduceAttemptsThenLockTheAccount() {
        final FakeDataAccess dataAccess = userDataAccess();
        final InMemoryTracker tracker = new InMemoryTracker();
        SecurityQuestionOutputData lastFailure = null;

        for (int attempt = 1; attempt <= AccountLockout.MAX_ATTEMPTS; attempt++) {
            final RecordingPresenter presenter = new RecordingPresenter();
            new SecurityQuestionInteractor(dataAccess, presenter, tracker)
                    .verifyAnswer(new SecurityQuestionInputData("yidan", "wrong"));
            lastFailure = presenter.failure;
            if (attempt < AccountLockout.MAX_ATTEMPTS) {
                assertFalse(lastFailure.isLockedOut());
                assertEquals(AccountLockout.MAX_ATTEMPTS - attempt,
                        lastFailure.getRemainingAttempts());
            }
        }

        assertTrue(lastFailure.isLockedOut());
        assertEquals(0, lastFailure.getRemainingAttempts());
        assertTrue(lastFailure.getLockRemainingSeconds() > 0);
    }

    private static FakeDataAccess userDataAccess() {
        final FakeDataAccess dataAccess = new FakeDataAccess();
        dataAccess.user = new StandardUser("yidan", "Yidan", "password1", "First pet?", "Mochi");
        return dataAccess;
    }
}
