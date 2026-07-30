package interface_adapter.account;

import interface_adapter.ViewModel;

/**
 * The View Model for the Account View.
 */
public class AccountViewModel extends ViewModel<AccountState> {

    public static final String TITLE_LABEL = "My Account";
    public static final String CUSTOMIZE_BUTTON = "Customize Profile";
    public static final String LOGOUT_BUTTON = "Logout";
    public static final String RESET_PASSWORD_BUTTON = "Reset Password";
    public static final String DELETE_ACCOUNT_BUTTON = "Delete Account";

    public static final String WATCHLIST_BUTTON = "View my watch list";
    public static final String WATCH_HISTORY_BUTTON = "View my watch history";
    public static final String REVIEWS_BUTTON = "View my reviews";
    public static final String BLOCKED_USERS_BUTTON = "View my blocked users";

    public AccountViewModel() {
        super("account");
        setState(new AccountState());
    }

}
