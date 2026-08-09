package interface_adapter.search;

import interface_adapter.StateModel;

/**
 * The View Model for the Search View.
 */
public class SearchViewModel extends StateModel<SearchState> {

    public static final String VIEW_NAME = "search";

    public static final String TITLE_LABEL = "Search";

    public SearchViewModel() {
        super(VIEW_NAME);
        setState(new SearchState());
    }
}
