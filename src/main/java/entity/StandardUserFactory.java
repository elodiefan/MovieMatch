package entity;

/** Factory for creating StandardUser objects. */

public class StandardUserFactory implements UserFactory {

    @Override
    public User create(String username, String displayName, String password,
                       String securityQuestion, String answer) {
        return new StandardUser(username, displayName, password, securityQuestion, answer);
    }

    @Override
    public User create(String username, String displayName, String password,
                String securityQuestion, String answer, UserLists userLists) {
        return new StandardUser(username, displayName, password,
                securityQuestion, answer, userLists);
    }
}
