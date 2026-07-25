package use_case.signup;

import entities.User;
import entities.UserFactory;

/**
 * Interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    /**
     * Creates a signup interactor.
     *
     * @param signupDataAccessInterface the data access object used to check and save users
     * @param signupOutputBoundary the presenter used to prepare signup success or failure output
     * @param userFactory the factory used to create new user objects
     */
    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    /**
     * Executes the signup use case.
     *
     * @param signupInputData the user's submitted signup information
     */
    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = trimToEmpty(signupInputData.getUsername());
        final String displayName = trimToEmpty(signupInputData.getDisplayName());
        final String securityQuestion = trimToEmpty(signupInputData.getSecurityQuestion());
        final String securityAnswer = trimToEmpty(signupInputData.getSecurityAnswer());

        if (isBlank(username)) {
            userPresenter.prepareFailView("Username cannot be empty.");
        }
        else if (isBlank(displayName)) {
            userPresenter.prepareFailView("Display name cannot be empty.");
        }
        else if (isBlank(signupInputData.getPassword())) {
            userPresenter.prepareFailView("Password cannot be empty.");
        }
        else if (isBlank(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Repeated password cannot be empty.");
        }
        else if (isBlank(securityQuestion)) {
            userPresenter.prepareFailView("Security question cannot be empty.");
        }
        else if (isBlank(securityAnswer)) {
            userPresenter.prepareFailView("Security answer cannot be empty.");
        }
        else if (userDataAccessObject.existsByUsername(username)) {
            userPresenter.prepareFailView("Username already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {
            final User user = userFactory.create(
                    username,
                    displayName,
                    signupInputData.getPassword(),
                    securityQuestion,
                    securityAnswer
            );
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getName(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    /**
     * Switches from the signup view to the login view.
     */
    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }

    /**
     * Checks whether a submitted text field is empty or only whitespace.
     *
     * @param value the submitted text value to check
     * @return true if the value is null, empty, or only whitespace; false otherwise
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Removes leading and trailing whitespace from submitted text.
     *
     * @param value the submitted text value to trim
     * @return the trimmed value, or an empty string if the value is null
     */
    private String trimToEmpty(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }
}
