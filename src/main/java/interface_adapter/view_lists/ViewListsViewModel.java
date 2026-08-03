package interface_adapter.view_lists;

import interface_adapter.ViewModel;

/**
 * The View Model for the View Lists View.
 */
public class ViewListsViewModel extends ViewModel<ViewListsState> {

    public static final String VIEW_NAME = "view lists";
    public static final String WATCHLIST = "My watchlist";
    public static final String WATCH_HISTORY = "My watch history";
    public static final String REVIEWS = "My reviews";
    public static final String BLOCKED_USERS = "My blocked users";

    public ViewListsViewModel() {
        super("view lists");
        setState(new ViewListsState());
    }

}
