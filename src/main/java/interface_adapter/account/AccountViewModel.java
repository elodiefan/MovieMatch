package interface_adapter.account;

import interface_adapter.ViewModel;

/**
 * The View Model for the Account View.
 */
public class AccountViewModel extends ViewModel<AccountState> {

    public static final String VIEW_NAME = "account";
    public static final String TITLE_LABEL = "My Account";
    public static final String UPGRADE_TO_PREMIUM_BUTTON = "Upgrade to Premium";
    public static final String CUSTOMIZE_BUTTON = "Customize Profile";
    public static final String LOGOUT_BUTTON = "Logout";
    public static final String RESET_PASSWORD_BUTTON = "Reset Password";
    public static final String DELETE_ACCOUNT_BUTTON = "Delete Account";

    public static final String WATCHLIST_BUTTON = "View watchlist";
    public static final String WATCH_HISTORY_BUTTON = "View watch history";
    public static final String REVIEWS_BUTTON = "View reviews";
    public static final String BLOCKED_USERS_BUTTON = "View blocked users";

    public AccountViewModel() {
        super("account");
        setState(new AccountState());
    }

}
