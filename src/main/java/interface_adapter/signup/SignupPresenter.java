package interface_adapter.signup;

import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final Runnable loginViewHandler;

    /**
     * Creates a signup presenter.
     */
    public SignupPresenter(SignupViewModel signupViewModel) {
        this(signupViewModel, null);
    }

    /**
     * Creates a signup presenter that can update app navigation.
     */
    public SignupPresenter(SignupViewModel signupViewModel,
                           Runnable loginViewHandler) {
        this.signupViewModel = signupViewModel;
        this.loginViewHandler = loginViewHandler;
    }

    /**
     * Prepares the signup view after a successful signup.
     */
    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsername(outputData.getUsername());
        signupState.setDisplayName(outputData.getDisplayName());
        signupState.setPassword("");
        signupState.setRepeatPassword("");
        signupState.setSecurityAnswer("");
        signupState.setSignupError(null);
        signupViewModel.setState(signupState);
        signupViewModel.firePropertyChanged();
        switchToLoginView();
    }

    /**
     * Prepares the signup view after a failed signup.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setSignupError(errorMessage);
        signupViewModel.setState(signupState);
        signupViewModel.firePropertyChanged();
    }

    /**
     * Switches from the signup view to the login view.
     */
    @Override
    public void switchToLoginView() {
        if (loginViewHandler != null) {
            loginViewHandler.run();
        }
    }
}
