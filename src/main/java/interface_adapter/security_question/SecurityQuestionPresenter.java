package interface_adapter.security_question;

import interface_adapter.ViewManagerModel;
import interface_adapter.reset_password.PasswordResetCompletedHandler;
import interface_adapter.reset_password.ResetPasswordState;
import interface_adapter.reset_password.ResetPasswordViewModel;
import use_case.security_question.SecurityQuestionOutputBoundary;
import use_case.security_question.SecurityQuestionOutputData;

/**
 * Presenter for the Security Question use case.
 * <p>
 * Translates use-case results into view-model state changes:
 * <ul>
 *     <li>#presentSecurityQuestion — put the question on screen;</li>
 *     <li>#prepareSuccessView — identity confirmed, so seed the
 *     reset-password screen with the verified username and switch to it via the
 *     ViewManagerModel;</li>
 *     <li>#prepareFailView — show a wrong-answer / locked-out / no-account
 *     message and (when locked) disable the inputs.</li>
 * </ul>
 * <p>
 * It also implements PasswordResetCompletedHandler: once the password
 * has actually been changed, this screen is where the user is returned to, with
 * a confirmation message. Doing it here means the reset-password package never
 * has to know this package exists.
 */
public class SecurityQuestionPresenter
        implements SecurityQuestionOutputBoundary, PasswordResetCompletedHandler {

    private final SecurityQuestionViewModel securityQuestionViewModel;
    private final ResetPasswordViewModel resetPasswordViewModel;
    private final ViewManagerModel viewManagerModel;

    public SecurityQuestionPresenter(SecurityQuestionViewModel securityQuestionViewModel,
                                     ResetPasswordViewModel resetPasswordViewModel,
                                     ViewManagerModel viewManagerModel) {
        this.securityQuestionViewModel = securityQuestionViewModel;
        this.resetPasswordViewModel = resetPasswordViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentSecurityQuestion(SecurityQuestionOutputData outputData) {
        final SecurityQuestionState state = securityQuestionViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setSecurityQuestion(outputData.getSecurityQuestion());
        state.setLockedOut(false);
        state.setError("");
        state.setMessage("");
        securityQuestionViewModel.setState(state);
        securityQuestionViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(SecurityQuestionOutputData outputData) {
        // Note the success on our own view model...
        final SecurityQuestionState state = securityQuestionViewModel.getState();
        state.setError("");
        state.setLockedOut(false);
        state.setMessage("Answer correct — you can now change your password.");
        securityQuestionViewModel.setState(state);
        securityQuestionViewModel.firePropertyChanged();

        // ...seed the reset-password screen with the verified username...
        final ResetPasswordState resetState = resetPasswordViewModel.getState();
        resetState.setUsername(outputData.getUsername());
        resetState.setNewPassword("");
        resetState.setConfirmPassword("");
        resetState.setMessage("");
        resetState.setError("");
        resetPasswordViewModel.setState(resetState);
        resetPasswordViewModel.firePropertyChanged();

        // ...and switch the active view to it.
        viewManagerModel.setState(resetPasswordViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(SecurityQuestionOutputData outputData) {
        final SecurityQuestionState state = securityQuestionViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setSecurityQuestion(outputData.getSecurityQuestion());
        state.setLockedOut(outputData.isLockedOut());
        state.setMessage("");

        if (outputData.isLockedOut()) {
            // Locked: tell them how long to wait (shown in minutes, rounded up).
            final long minutes = (outputData.getLockRemainingSeconds() + 59) / 60;
            state.setError("Too many incorrect attempts. Account locked for about "
                    + minutes + " minute(s). Please try again later.");
        }
        else if (outputData.getSecurityQuestion().isEmpty()) {
            // Empty question + failure means the account wasn't found.
            state.setError("No account found with that username.");
        }
        else {
            state.setError("Incorrect answer. Attempts remaining: "
                    + outputData.getRemainingAttempts() + ".");
        }

        securityQuestionViewModel.setState(state);
        securityQuestionViewModel.firePropertyChanged();
    }

    /**
     * The password has been changed, so clear this screen, confirm what
     * happened, and bring the user back to it.
     */
    @Override
    public void passwordResetCompleted(String username) {
        final SecurityQuestionState state = securityQuestionViewModel.getState();
        state.setUsername("");
        state.setSecurityQuestion("");
        state.setAnswer("");
        state.setError("");
        state.setLockedOut(false);
        state.setMessage("Password changed successfully for " + username + ".");
        securityQuestionViewModel.setState(state);
        securityQuestionViewModel.firePropertyChanged();

        viewManagerModel.setState(securityQuestionViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
