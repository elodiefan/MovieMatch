package database;

import java.util.HashMap;
import java.util.Map;

import entity.AccountLockout;
import use_case.security_question.LockoutTracker;

/** In-memory implementation of LockoutTracker. */
public class InMemoryLockoutTracker implements LockoutTracker {

    private final Map<String, AccountLockout> lockouts = new HashMap<>();

    @Override
    public AccountLockout forUser(String username) {
        return lockouts.computeIfAbsent(username, key -> new AccountLockout());
    }
}
