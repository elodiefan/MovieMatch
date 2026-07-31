package interface_adapter.delete_account;

/**
 * The state for the Delete Account View Model.
 */

public class DeleteAccountState {
    private String username;
    private String deleteAccountError;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeleteAccountError() {
        return deleteAccountError;
    }

    public void setDeleteAccountError(String deleteAccountError) {
        this.deleteAccountError = deleteAccountError;
    }
}
