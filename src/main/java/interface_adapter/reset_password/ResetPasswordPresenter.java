package interface_adapter.reset_password;

import use_case.reset_password.ResetPasswordOutputBoundary;
import use_case.reset_password.ResetPasswordOutputData;

/** Presenter for the Reset Password use case. */
public class ResetPasswordPresenter implements ResetPasswordOutputBoundary {

    private final ResetPasswordViewModel resetPasswordViewModel;
    private final PasswordResetCompletedHandler completedHandler;

    public ResetPasswordPresenter(ResetPasswordViewModel resetPasswordViewModel,
                                  PasswordResetCompletedHandler completedHandler) {
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.completedHandler = completedHandler;
    }

    @Override
    public void prepareSuccessView(ResetPasswordOutputData outputData) {
        // Clear the password form so the typed values do not linger.
        final ResetPasswordState state = resetPasswordViewModel.getState();
        state.setError("");
        state.setMessage("");
        state.setNewPassword("");
        state.setConfirmPassword("");
        resetPasswordViewModel.setState(state);
        resetPasswordViewModel.firePropertyChanged();

        // Someone else decides which screen comes next.
        completedHandler.passwordResetCompleted(outputData.getUsername());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Stay on this screen: the user needs to fix the input.
        final ResetPasswordState state = resetPasswordViewModel.getState();
        state.setMessage("");
        state.setError(errorMessage);
        resetPasswordViewModel.setState(state);
        resetPasswordViewModel.firePropertyChanged();
    }
}