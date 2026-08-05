package interface_adapter.get_lists;

import interface_adapter.ViewModel;

/**
 * The View Model for the Watchlist View.
 */
public class GetBlockedUsersViewModel extends ViewModel<GetListsState> {

    public static final String VIEW_NAME = "watchlist";
    public static final String BLOCKED_USERS = "'s blocked users";
    public static final String RETURN_BUTTON = "Return to account";

    public GetBlockedUsersViewModel() {
        super("watchlist");
        setState(new GetListsState());
    }

}
