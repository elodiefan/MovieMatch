package use_case.reset_password;

/**
 * Interactor for the Reset Password use case.
 * <p>
 * Validates the new password, then writes it through the DAO. Rules:
 * <ul>
 * <li>the new password must not be blank;</li>
 * <li>it must be at least {@link #MIN_LENGTH} characters;</li>
 * <li>the "new" and "confirm" entries must match.</li>
 * </ul>
 * On success it reports back through the presenter; on any rule violation it
 * reports a fail with an explanation and does not touch storage.
 */
public class ResetPasswordInteractor implements ResetPasswordInputBoundary {

    /**
     * Minimum allowed password length.
     */
    static final int MIN_LENGTH = 4;

    private final ResetPasswordUserDataAccessInterface userDataAccessObject;
    private final ResetPasswordOutputBoundary presenter;

    public ResetPasswordInteractor(ResetPasswordUserDataAccessInterface userDataAccessObject,
                                   ResetPasswordOutputBoundary presenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void changePassword(ResetPasswordInputData inputData) {
        final String username = inputData.getUsername();
        final String newPassword = inputData.getNewPassword();
        final String confirmPassword = inputData.getConfirmPassword();

        // Defensive: the account should exist (identity was just verified), but check anyway.
        if (!userDataAccessObject.existsByName(username)) {
            presenter.prepareFailView("No account found with that username.");
            return;
        }

        // Validation rules.
        if (newPassword == null || newPassword.isEmpty()) {
            presenter.prepareFailView("Password cannot be empty.");
            return;
        }
        if (newPassword.length() < MIN_LENGTH) {
            presenter.prepareFailView("Password must be at least " + MIN_LENGTH + " characters.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            presenter.prepareFailView("The two passwords do not match.");
            return;
        }

        // All good â€” persist the change and report success.
        userDataAccessObject.changePassword(username, newPassword);
        presenter.prepareSuccessView(new ResetPasswordOutputData(username, false));
    }
}
