package entity;

/**
 * Factory for creating users.
 */
public interface UserFactory {
    /**
     * Creates a new User.
     * @param username the name of the new user
     * @param displayName the display name of the user.
     * @param securityQuestion the user's chosen security question
     * @param securityAnswer the answer to the user's chosen security question.
     * @param password the password of the new user
     * @param userLists the object storing the user's watchlist, watch history, reviews, and blocked users.
     * @return the new user
     */
    User create(String username, String displayName, String password,
                String securityQuestion, String securityAnswer, UserLists userLists);

}
