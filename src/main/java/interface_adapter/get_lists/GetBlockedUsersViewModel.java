package interface_adapter.get_lists;

import interface_adapter.StateModel;

/**
 * The View Model for the Watchlist View.
 */
public class GetBlockedUsersViewModel extends StateModel<GetListsState> {

    public static final String VIEW_NAME = "blocked users";
    public static final String BLOCKED_USERS = "'s blocked users";
    public static final String RETURN_BUTTON = "Return to account";

    public GetBlockedUsersViewModel() {
        super("blocked users");
        setState(new GetListsState());
    }

}
