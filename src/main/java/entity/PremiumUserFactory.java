package entity;

/** Factory for creating VIPUser objects. */

public class PremiumUserFactory implements UserFactory {

    @Override
    public User create(String username, String displayName, String password,
                       String securityQuestion, String answer) {
        return new PremiumUser(username, displayName, password,
                securityQuestion, answer);
    }

    @Override
    public User create(String username, String displayName, String password,
                       String securityQuestion, String answer, UserLists userLists) {
        return new PremiumUser(username, displayName, password,
                securityQuestion, answer, userLists);
    }
}
