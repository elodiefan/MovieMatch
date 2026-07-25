package use_case.signup;

import entities.User;
import entities.UserFactory;

/**
 * Interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MAX_DISPLAY_NAME_LENGTH = 30;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 64;
    private static final int MAX_SECURITY_ANSWER_LENGTH = 100;
    private static final String USERNAME_PATTERN = "[A-Za-z0-9_]+";

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
        final String password = signupInputData.getPassword();
        final String repeatPassword = signupInputData.getRepeatPassword();

        if (isBlank(username)) {
            userPresenter.prepareFailView("Username cannot be empty.");
        }
        else if (!isValidUsername(username)) {
            userPresenter.prepareFailView(
                    "Username must be 3 to 20 characters and use only letters, numbers, or underscores."
            );
        }
        else if (isBlank(displayName)) {
            userPresenter.prepareFailView("Display name cannot be empty.");
        }
        else if (displayName.length() > MAX_DISPLAY_NAME_LENGTH) {
            userPresenter.prepareFailView("Display name cannot be longer than 30 characters.");
        }
        else if (isBlank(password)) {
            userPresenter.prepareFailView("Password cannot be empty.");
        }
        else if (hasLeadingOrTrailingWhitespace(password)) {
            userPresenter.prepareFailView("Password cannot start or end with spaces.");
        }
        else if (!isValidPassword(password)) {
            userPresenter.prepareFailView(
                    "Password must be 8 to 64 characters and include at least one letter and one number."
            );
        }
        else if (isBlank(repeatPassword)) {
            userPresenter.prepareFailView("Repeated password cannot be empty.");
        }
        else if (hasLeadingOrTrailingWhitespace(repeatPassword)) {
            userPresenter.prepareFailView("Repeated password cannot start or end with spaces.");
        }
        else if (isBlank(securityQuestion)) {
            userPresenter.prepareFailView("Security question cannot be empty.");
        }
        else if (isBlank(securityAnswer)) {
            userPresenter.prepareFailView("Security answer cannot be empty.");
        }
        else if (securityAnswer.length() > MAX_SECURITY_ANSWER_LENGTH) {
            userPresenter.prepareFailView("Security answer cannot be longer than 100 characters.");
        }
        else if (userDataAccessObject.existsByUsername(username)) {
            userPresenter.prepareFailView("Username already exists.");
        }
        else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else {
            final User user = userFactory.create(
                    username,
                    displayName,
                    password,
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
     * Checks whether a username follows the signup rules.
     *
     * @param username the trimmed username to check
     * @return true if the username has a valid length and characters; false otherwise
     */
    private boolean isValidUsername(String username) {
        return username.length() >= MIN_USERNAME_LENGTH
                && username.length() <= MAX_USERNAME_LENGTH
                && username.matches(USERNAME_PATTERN);
    }

    /**
     * Checks whether a password follows the signup rules.
     *
     * @param password the password to check
     * @return true if the password has a valid length, letter, and number; false otherwise
     */
    private boolean isValidPassword(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH
                && containsLetter(password)
                && containsNumber(password);
    }

    /**
     * Checks whether a password contains at least one letter.
     *
     * @param password the password to check
     * @return true if the password contains a letter; false otherwise
     */
    private boolean containsLetter(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLetter(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a password contains at least one number.
     *
     * @param password the password to check
     * @return true if the password contains a number; false otherwise
     */
    private boolean containsNumber(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether text starts or ends with whitespace.
     *
     * @param value the submitted text value to check
     * @return true if the value starts or ends with whitespace; false otherwise
     */
    private boolean hasLeadingOrTrailingWhitespace(String value) {
        return value != null && !value.equals(value.trim());
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
