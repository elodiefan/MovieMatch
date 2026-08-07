package interface_adapter.logout;

import interface_adapter.ViewModel;

/** The View Model for the Logout Confirmation View. */
public class LogoutViewModel extends ViewModel<LogoutState> {

    public LogoutViewModel() {
        super("logout confirm");
        setState(new LogoutState());
    }
}
