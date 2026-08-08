package interface_adapter.home_page;

import interface_adapter.StateModel;

public class HomePageViewModel extends StateModel<HomePageState> {

    public static final String VIEW_NAME = "home page";
    public static final String TITLE_LABEL = "Home Page View";
    public static final String RECOMMENDATIONS_LABEL = "Recommendations";

    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String FIND_USERS_BUTTON_LABEL = "Find Users";
    public static final String ACCOUNT_BUTTON_LABEL = "Your Account";
    public static final String SETTINGS_BUTTON_LABEL = "Settings";

    public HomePageViewModel() {
        super("home page");
        setState(new HomePageState());
    }
}
