package interface_adapter.personal_account;

import interface_adapter.ViewModel;

/**
 * The View Model for the Account View.
 */
public class PersonalAccountViewModel extends ViewModel<PersonalAccountState> {

    public static final String VIEW_NAME = "personal account";
    public static final String TITLE_LABEL = "My Account";
    public static final String USERNAME_LABEL = "username: ";
    public static final String DISPLAY_NAME_LABEL = "display name: ";
    public static final String CUSTOMIZE_BUTTON = "Customize Profile";
    public static final String LOGOUT_BUTTON = "Logout";
    public static final String RESET_PASSWORD_BUTTON = "Reset Password";
    public static final String DELETE_ACCOUNT_BUTTON = "Delete Account";

    public static final String WATCHLIST_BUTTON = "View watch list";
    public static final String WATCH_HISTORY_BUTTON = "View watch history";
    public static final String REVIEWS_BUTTON = "View reviews";
    public static final String BLOCKED_USERS_BUTTON = "View blocked users";
    public static final String BACK_BUTTON = "Back";

    public PersonalAccountViewModel() {
        super("personal account");
        setState(new PersonalAccountState());
    }

}
