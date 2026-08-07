package interface_adapter.search_user;

import interface_adapter.ViewModel;

/** The View Model for the Search User View. */
public class SearchUserViewModel extends ViewModel<SearchUserState> {

    public static final String VIEW_NAME = "search user";
    public static final String TITLE_LABEL = "Find Users";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String VIEW_PROFILE_BUTTON_LABEL = "View Profile";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String NO_RESULTS_LABEL = "No users found for ";
    public static final String PROMPT_LABEL = "Search by username or display name.";

    public SearchUserViewModel() {
        super(VIEW_NAME);
        setState(new SearchUserState());
    }
}
