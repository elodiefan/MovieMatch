package interface_adapter.recommendation;

import interface_adapter.ViewModel;

/**
 * The View Model for the detailed recommendation screen.
 */
public class RecommendationViewModel extends ViewModel<RecommendationState> {

    public static final String VIEW_NAME = "recommendations";

    public static final String TITLE_LABEL = "Recommended for you";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String REFRESH_BUTTON_LABEL = "Refresh";
    public static final String LOADING_LABEL = "Finding titles you might like...";
    public static final String EMPTY_LABEL =
            "Nothing to suggest yet. Add something to your watchlist and try again.";

    public RecommendationViewModel() {
        super(VIEW_NAME);
        setState(new RecommendationState());
    }
}
