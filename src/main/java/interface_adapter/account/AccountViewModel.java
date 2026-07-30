package interface_adapter.account;

import interface_adapter.ViewModel;

/**
 * The View Model for the Account View.
 */
public class AccountViewModel extends ViewModel<AccountState> {

    public AccountViewModel() {
        super("account");
        setState(new AccountState());
    }

}
