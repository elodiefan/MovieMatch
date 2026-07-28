package data_access;

import entity.User;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.delete_account.DeleteAccountUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * The storage contract for the whole app.
 * <p>
 * It gathers every use case's data-access interface into a single interface, so
 * a data store only has to implement this one thing to satisfy all of them.
 * Swapping databases means writing a new class that implements this interface
 * and changing one line in {@code AppBuilder} — no use case, interactor,
 * presenter, or view changes.
 * <p>
 * Implemented by {@link MongoUserDataAccessObject} (the real MongoDB Atlas
 * database) and {@link InMemoryUserDataAccessObject} (plain maps, for running
 * and testing without a network).
 * <p>
 * <strong>When you add a use case:</strong> write its own
 * {@code ...UserDataAccessInterface} in your {@code use_case} package, add it to
 * the {@code extends} list below, then implement the new methods in both
 * classes. Nothing else needs to change.
 */
public interface UserDataAccessObject extends
        SignupUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        DeleteAccountUserDataAccessInterface {

    // NOTE: LoginUserDataAccessInterface is deliberately not extended yet.
    // The file use_case/login/LoginUserDataAccessInterface.java currently holds
    // a copy of the Logout interface, so the Login type does not exist to
    // extend. Once that file declares LoginUserDataAccessInterface properly,
    // add it to the extends list above and delete the get(...) declaration
    // below, which it already provides. Everything else stays the same.

    /**
     * Returns the user with the given username.
     * @param username the account to look up
     * @return the matching user, or null if there is no such account
     */
    User get(String username);

    /**
     * Releases any resources held by this data store, such as an open database
     * connection. Call once when the app shuts down. Implementations with
     * nothing to release may do nothing.
     */
    void close();
}
