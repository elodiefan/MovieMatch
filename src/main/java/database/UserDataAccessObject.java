package database;

import use_case.access_message_chat.AccessMessageChatUserDataAccessInterface;
import use_case.block_user.BlockUserUserDataAccessInterface;
import use_case.change_display_name.ChangeDisplayNameUserDataAccessInterface;
import use_case.change_username.ChangeUsernameUserDataAccessInterface;
import use_case.delete_account.DeleteAccountUserDataAccessInterface;
import use_case.get_lists.get_blocked_users.GetBlockedUsersUserDataAccessInterface;
import use_case.get_profile.GetProfileUserDataAccessInterface;
import use_case.get_security_question.GetSecurityQuestionUserDataAccessInterface;
import use_case.get_lists.get_watch_history.GetWatchHistoryUserDataAccessInterface;
import use_case.get_lists.get_watchlist.GetWatchlistUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.log_media.LogMediaDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.reset_password.ResetPasswordUserDataAccessInterface;
import use_case.recommendation.WatchedMediaDataAccessInterface;
import use_case.review.create_review.CreateReviewUserDataAccessInterface;
import use_case.search_user.SearchUserDataAccess;
import use_case.security_question.SecurityQuestionUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * The storage contract for the whole app.
 */
public interface UserDataAccessObject extends
        AccessMessageChatUserDataAccessInterface,
        BlockUserUserDataAccessInterface,
        ChangeDisplayNameUserDataAccessInterface,
        ChangeUsernameUserDataAccessInterface,
        CreateReviewUserDataAccessInterface,
        DeleteAccountUserDataAccessInterface,
        GetBlockedUsersUserDataAccessInterface,
        GetWatchHistoryUserDataAccessInterface,
        GetWatchlistUserDataAccessInterface,
        GetProfileUserDataAccessInterface,
        GetSecurityQuestionUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogMediaDataAccessInterface,
        LogoutUserDataAccessInterface,
        ResetPasswordUserDataAccessInterface,
        SearchUserDataAccess,
        SecurityQuestionUserDataAccessInterface,
        SignupUserDataAccessInterface,
        WatchedMediaDataAccessInterface {

    /**
     * Releases any resources held by this data store, such as an open database connection.
     */
    void close();
}
