package entity;

/**
 * Represents a standard user of the app.
 */

public class StandardUser implements User {

    private final String name;
    private final String password;

    public StandardUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

}