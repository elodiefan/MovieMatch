package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {

    private final SignupInputBoundary signupUseCaseInteractor;

    /**
     * Creates a signup controller.
     */
    public SignupController(SignupInputBoundary signupUseCaseInteractor) {
        this.signupUseCaseInteractor = signupUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     */
    public void execute(String username, String displayName, String password,
                        String repeatPassword, String securityQuestion, String securityAnswer) {
        final SignupInputData signupInputData = new SignupInputData(username, displayName, password,
                repeatPassword, securityQuestion, securityAnswer);
        signupUseCaseInteractor.execute(signupInputData);
    }

    /**
     * Switches from the signup view to the login view.
     */
    public void switchToLoginView() {
        signupUseCaseInteractor.switchToLoginView();
    }
}
