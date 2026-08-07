package entity;

/** Factory for creating users. */
public interface UserFactory {
    /** Creates a new User with default list settings. */
    User create(String username, String displayName, String password,
                String securityQuestion, String securityAnswer);

    /** Creates a new User. */
    User create(String username, String displayName, String password,
                String securityQuestion, String securityAnswer, UserLists userLists);

}
