package interface_adapter.log_media;

import interface_adapter.StateModel;

/**
 * View model for logging media to user lists.
 */
public class LogMediaViewModel extends StateModel<LogMediaState> {
    /** Button label for watchlist logging. */
    public static final String WATCHLIST_BUTTON_LABEL = "Want to watch";
    /** Button label for watch history logging. */
    public static final String WATCH_HISTORY_BUTTON_LABEL =
            "Mark as watched";

    /**
     * Creates a log media view model.
     */
    public LogMediaViewModel() {
        super("log media");
        setState(new LogMediaState());
    }
}
