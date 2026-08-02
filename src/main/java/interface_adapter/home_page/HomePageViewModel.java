package interface_adapter.home_page;

import interface_adapter.ViewModel;

public class HomePageViewModel extends ViewModel<HomePageState> {

    public static final String VIEW_NAME = "home page";
    public static final String TITLE_LABEL = "Home Page View";
    public static final String RECOMMENDATIONS_LABEL = "Recommendations";

    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String ACCOUNT_BUTTON_LABEL = "Your Account";

    public HomePageViewModel() {
        super("home page");
        setState(new HomePageState());
    }
}
