package interface_adapter.get_lists;

import interface_adapter.StateModel;

/**
 * The View Model for the Watchlist View.
 */
public class GetWatchHistoryViewModel extends StateModel<GetListsState> {

    public static final String VIEW_NAME = "watch history";
    public static final String WATCH_HISTORY = "'s watch history";
    public static final String RETURN_BUTTON = "Return to account";

    public GetWatchHistoryViewModel() {
        super("watch history");
        setState(new GetListsState());
    }

}
