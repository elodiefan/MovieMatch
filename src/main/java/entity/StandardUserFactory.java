package entity;

/**
 * Factory for creating StandardUser objects.
 */

public class StandardUserFactory implements UserFactory {

    @Override
    public User create(String username, String displayName, String password, String securityQuestion, String securityAnswer) {
        return new StandardUser(username, displayName, password, securityQuestion, securityAnswer);
    }
}