package use_case.reset_password;

/** Output data for the Reset Password use case. */
public class ResetPasswordOutputData {

    private final String username;
    private final boolean useCaseFailed;

    public ResetPasswordOutputData(String username, boolean useCaseFailed) {
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
