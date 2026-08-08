package use_case.delete_account;

/**
 * Output Data for the Delete Account Use Case.
 */
public class DeleteAccountOutputData {

    private String username;
    private boolean useCaseFailed;

    public DeleteAccountOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
