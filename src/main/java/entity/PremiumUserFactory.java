package entity;

/**
 * Factory for creating VIPUser objects.
 */

public class PremiumUserFactory implements UserFactory {

    @Override
    public User create(String username, String displayName, String password,
                       String securityQuestion, String securityAnswer) {
        return new PremiumUser(username, displayName, password,
                securityQuestion, securityAnswer);
    }
}
