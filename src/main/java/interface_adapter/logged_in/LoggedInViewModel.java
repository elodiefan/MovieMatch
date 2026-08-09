package interface_adapter.logged_in;

import interface_adapter.StateModel;

/**
 * The View Model for the Logged In View.
 */
public class LoggedInViewModel extends StateModel<LoggedInState> {

    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedInState());
    }

}
