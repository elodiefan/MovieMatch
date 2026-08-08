package interface_adapter.delete_account;

import interface_adapter.StateModel;

/**
 * The View Model for the Delete Account View.
 */

public class DeleteAccountViewModel extends StateModel<DeleteAccountState> {

    public static final String TITLE_LABEL = "Delete Account Screen";
    public static final String DESCRIPTION = "Correctly answer the security question to delete the account ";
    public static final String SECURITY_TITLE = "Security Question: ";

    public static final String DELETE_ACCOUNT_BUTTON = "Delete Account";
    public static final String CANCEL_BUTTON = "Cancel";

    public DeleteAccountViewModel() {
        super("delete account");
        setState(new DeleteAccountState());
    }
}
