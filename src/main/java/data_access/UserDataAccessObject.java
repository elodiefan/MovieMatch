package data_access;

import use_case.access_message_chat.AccessMessageChatUserDataAccessInterface;
import use_case.block_user.BlockUserUserDataAccessInterface;
import use_case.delete_account.DeleteAccountUserDataAccessInterface;
import use_case.get_lists.GetListsUserDataAccessInterface;
import use_case.get_profile.GetProfileUserDataAccessInterface;
import use_case.get_security_question.GetSecurityQuestionUserDataAccessInterface;
import use_case.home_page.HomePageUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.log_media.LogMediaDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.reset_password.ResetPasswordUserDataAccessInterface;
import use_case.security_question.SecurityQuestionUserDataAccessInterface;
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
        AccessMessageChatUserDataAccessInterface,
        BlockUserUserDataAccessInterface,
        DeleteAccountUserDataAccessInterface,
        GetListsUserDataAccessInterface,
        GetProfileUserDataAccessInterface,
        GetSecurityQuestionUserDataAccessInterface,
        HomePageUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogMediaDataAccessInterface,
        LogoutUserDataAccessInterface,
        ResetPasswordUserDataAccessInterface,
        SecurityQuestionUserDataAccessInterface,
        SignupUserDataAccessInterface {

    /**
     * Releases any resources held by this data store, such as an open database
     * connection. Call once when the app shuts down. Implementations with
     * nothing to release may do nothing.
     */
    void close();
}
