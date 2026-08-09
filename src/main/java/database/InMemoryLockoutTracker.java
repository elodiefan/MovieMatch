package database;

import java.util.HashMap;
import java.util.Map;

import entity.AccountLockout;
import use_case.security_question.LockoutTracker;

/**
 * In-memory implementation of {@link LockoutTracker}.
 * Holds one {@link AccountLockout} per username for as long as the app runs, so
 * failed attempts and lock-outs are forgotten on restart. That is fine for a
 * desktop app with a single running copy. Storing them in the database instead
 * would only mean writing another class that implements the same interface.
 */
public class InMemoryLockoutTracker implements LockoutTracker {

    private final Map<String, AccountLockout> lockouts = new HashMap<>();

    @Override
    public AccountLockout forUser(String username) {
        return lockouts.computeIfAbsent(username, key -> new AccountLockout());
    }
}
