package interface_adapter.view_lists;

import interface_adapter.ViewModel;

/**
 * The View Model for the Watchlist View.
 */
public class GetWatchlistViewModel extends ViewModel<GetListsState> {

    public static final String VIEW_NAME = "watchlist";
    public static final String WATCHLIST = "'s watchlist";
    public static final String RETURN_BUTTON = "Return to account";

    public GetWatchlistViewModel() {
        super("watchlist");
        setState(new GetListsState());
    }

}
