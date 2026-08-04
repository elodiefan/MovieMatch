package entity;

/**
 * Represents a standard user of the app.
 */

public class StandardUser implements User {

    private final String username;
    private final String displayName;
    private final String password;
    private final String securityQuestion;
    private final String answer;
    private UserLists userLists;

    public StandardUser(String username, String displayName, String password,
                        String securityQuestion, String answer) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.userLists = new UserLists(username, "", "", "");
    }

    public StandardUser(String username, String displayName, String password,
                        String securityQuestion, String answer, UserLists userLists) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
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
    public String getAnswer() {
        return answer;
    }

    @Override
    public UserLists getUserLists() {
        return userLists;
    }

    @Override
    public String getWatchlist() {
        return userLists.getWatchlist();
    }

    @Override
    public String getWatchHistory() {
        return userLists.getWatchHistory();
    }

    @Override
    public String getBlockedUsers() {
        return userLists.getBlockedUsers();
    }

    @Override
    public void setUserLists(UserLists userLists) {
        this.userLists = userLists;
    }
}
