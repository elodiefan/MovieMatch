package interface_adapter.search_result;

import interface_adapter.ViewModel;

/**
 * The ViewModel for displaying search results.
 */
public class SearchResultViewModel extends ViewModel<SearchResultState> {

    public static final String VIEW_NAME = "search result";

    public static final String TITLE_LABEL = "Search Results";

    public SearchResultViewModel() {
        super(VIEW_NAME);
        setState(new SearchResultState());
    }
}
