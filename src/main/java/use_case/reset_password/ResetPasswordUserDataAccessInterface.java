package use_case.reset_password;

/** Data-access interface for the Reset Password use case. */
public interface ResetPasswordUserDataAccessInterface {

    /** Checks whether an account with the given username exists. */
    boolean existsByName(String username);

    /** Updates the stored password for the given user. */
    void changePassword(String username, String newPassword);
}
