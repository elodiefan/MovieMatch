package entity;

/**
 * Represents a standard user of the app.
 */

public class StandardUser implements User {

    private final String username;
    private final String displayName;
    private final String password;

    public StandardUser(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
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

}
