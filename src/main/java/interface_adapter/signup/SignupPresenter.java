package interface_adapter.signup;

import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;

    /**
     * Creates a signup presenter.
     *
     * @param signupViewModel the view model for the signup view
     */
    public SignupPresenter(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
    }

    /**
     * Prepares the signup view after a successful signup.
     *
     * @param outputData the successful signup output data
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
    }

    /**
     * Prepares the signup view after a failed signup.
     *
     * @param errorMessage the explanation of the signup failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setSignupError(errorMessage);
        signupViewModel.setState(signupState);
    }

    /**
     * Switches from the signup view to the login view.
     */
    @Override
    public void switchToLoginView() {
        signupViewModel.setState(signupViewModel.getState());
    }
}
