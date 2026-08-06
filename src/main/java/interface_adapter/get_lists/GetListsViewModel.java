package interface_adapter.get_lists;

import interface_adapter.ViewModel;

/**
 * The View Model for the View Lists View.
 */
public class GetListsViewModel extends ViewModel<GetListsState> {

    public static final String VIEW_NAME = "view lists";
    public static final String WATCHLIST = "'s watchlist";
    public static final String WATCH_HISTORY = "'s watch history";
    public static final String REVIEWS = "'s reviews";
    public static final String BLOCKED_USERS = "'s blocked users";
    public static final String LIST_LABEL = "'s lists";
    public static final String RETURN_BUTTON = "Return to account";

    public GetListsViewModel() {
        super("view lists");
        setState(new GetListsState());
    }

}
