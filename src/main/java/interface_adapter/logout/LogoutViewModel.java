package interface_adapter.logout;

import interface_adapter.StateModel;

/**
 * The View Model for the Logout Confirmation View.
 */
public class LogoutViewModel extends StateModel<LogoutState> {

    public LogoutViewModel() {
        super("logout confirm");
        setState(new LogoutState());
    }
}
