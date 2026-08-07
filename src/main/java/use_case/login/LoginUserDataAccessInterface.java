package use_case.login;

import entity.User;

/** DAO for the Login Use Case. */
public interface LoginUserDataAccessInterface {

    /** Checks if the given username exists. */
    boolean existsByUsername(String username);

    /** Saves the user. */
    void save(User user);

    /** Returns the user with the given username. */
    User get(String username);

    /** Returns the username of the current user. */
    String getCurrentUsername();

    /** Sets the username of the current user. */
    void setCurrentUsername(String username);

}
