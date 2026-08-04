package interface_adapter.view_lists;

import interface_adapter.ViewModel;

/**
 * The View Model for the Watchlist View.
 */
public class GetWatchHistoryViewModel extends ViewModel<GetListsState> {

    public static final String VIEW_NAME = "watch history";
    public static final String WATCH_HISTORY = "'s watch history";
    public static final String RETURN_BUTTON = "Return to account";

    public GetWatchHistoryViewModel() {
        super("watch history");
        setState(new GetListsState());
    }

}
