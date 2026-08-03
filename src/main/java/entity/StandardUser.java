package entity;

import java.util.List;

/**
 * Represents a standard user of the app.
 */

public class StandardUser implements User {

    private final String username;
    private final String displayName;
    private final String password;
    private final String securityQuestion;
    private final String securityAnswer;
    private final UserLists userLists;

    public StandardUser(String username, String displayName, String password,
                        String securityQuestion, String securityAnswer, UserLists userLists) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.userLists = userLists;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    @Override
    public String getSecurityAnswer() {
        return securityAnswer;
    }

    @Override
    public UserLists getUserLists() {
        return userLists;
    }

    @Override
    public List<Integer> getWatchlist() {
        return userLists.getWatchlist();
    }

    @Override
    public List<Integer> getWatchHistory() {
        return userLists.getWatchHistory();
    }

    @Override
    public List<Integer> getReviews() {
        return userLists.getReviews();
    }

    @Override
    public List<String> getBlockedUsers() {
        return userLists.getBlockedUsers();
    }
}
