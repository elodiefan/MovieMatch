package interface_adapter.other_account;

import interface_adapter.ViewModel;

/**
 * The View Model for the Account View.
 */
public class OtherAccountViewModel extends ViewModel<OtherAccountState> {

    public static final String VIEW_NAME = "other account";
    public static final String TITLE_LABEL = "'s Account";
    public static final String USERNAME_LABEL = "username: ";
    public static final String DISPLAY_NAME_LABEL = "display name: ";
    public static final String WATCHLIST_BUTTON = "View watch list";
    public static final String WATCH_HISTORY_BUTTON = "View watch history";
    public static final String REVIEWS_BUTTON = "View reviews";
    public static final String MESSAGE_BUTTON = "Message";
    public static final String BACK_BUTTON = "Back";

    public OtherAccountViewModel() {
        super("other account");
        setState(new OtherAccountState());
    }

}
