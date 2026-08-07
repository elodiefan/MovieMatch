package use_case.signup;

import entity.User;
import entity.UserFactory;

/**
 * Interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MAX_DISPLAY_NAME_LENGTH = 30;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final int MAX_SECURITY_ANSWER_LENGTH = 100;
    private static final String USERNAME_PATTERN = "\\w+";

    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    /**
     * Creates a signup interactor.
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
     */
    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = trimToEmpty(signupInputData.getUsername());
        final String displayName = trimToEmpty(signupInputData.getDisplayName());
        final String securityQuestion = trimToEmpty(signupInputData.getSecurityQuestion());
        final String securityAnswer = trimToEmpty(signupInputData.getSecurityAnswer());
        final String password = signupInputData.getPassword();
        final String repeatPassword = signupInputData.getRepeatPassword();
        final String errorMessage = validateSignupData(username, displayName, password,
                repeatPassword, securityQuestion, securityAnswer);

        if (errorMessage == null) {
            final User user = userFactory.create(
                    username,
                    displayName,
                    password,
                    securityQuestion,
                    securityAnswer
            );
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getUsername(), displayName);
            userPresenter.prepareSuccessView(signupOutputData);
        }
        else {
            userPresenter.prepareFailView(errorMessage);
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
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates all submitted signup fields.
     */
    private String validateSignupData(String username, String displayName, String password,
                                      String repeatPassword, String securityQuestion,
                                      String securityAnswer) {
        String errorMessage = validateUsername(username);
        if (errorMessage == null) {
            errorMessage = validateDisplayName(displayName);
        }
        if (errorMessage == null) {
            errorMessage = validatePassword(password, repeatPassword);
        }
        if (errorMessage == null) {
            errorMessage = validateSecurityInformation(securityQuestion, securityAnswer);
        }
        if (errorMessage == null && userDataAccessObject.existsByUsername(username)) {
            errorMessage = "Username already exists.";
        }
        return errorMessage;
    }

    /**
     * Validates a submitted username.
     */
    private String validateUsername(String username) {
        return validateUsernameFormat(username);
    }

    /**
     * Validates a submitted username's content and length.
     */
    private String validateUsernameFormat(String username) {
        final String errorMessage;
        if (isBlank(username)) {
            errorMessage = "Username cannot be empty.";
        }
        else {
            errorMessage = validateNonBlankUsername(username);
        }
        return errorMessage;
    }

    /**
     * Validates a submitted username after confirming it is not blank.
     */
    private String validateNonBlankUsername(String username) {
        final String errorMessage;
        if (!isValidUsername(username)) {
            errorMessage = "Username must be 3 to 20 characters and use only letters, numbers, "
                    + "or underscores.";
        }
        else {
            errorMessage = null;
        }
        return errorMessage;
    }

    /**
     * Validates a submitted display name.
     */
    private String validateDisplayName(String displayName) {
        final String errorMessage;
        if (isBlank(displayName)) {
            errorMessage = "Display name cannot be empty.";
        }
        else if (displayName.length() > MAX_DISPLAY_NAME_LENGTH) {
            errorMessage = "Display name cannot be longer than 30 characters.";
        }
        else {
            errorMessage = null;
        }
        return errorMessage;
    }

    /**
     * Validates submitted password fields.
     */
    private String validatePassword(String password, String repeatPassword) {
        final String errorMessage;
        if (isBlank(password)) {
            errorMessage = "Password cannot be empty.";
        }
        else if (hasLeadingOrTrailingWhitespace(password)) {
            errorMessage = "Password cannot start or end with spaces.";
        }
        else {
            errorMessage = validatePasswordStrength(password, repeatPassword);
        }
        return errorMessage;
    }

    /**
     * Validates submitted password strength and confirmation.
     */
    private String validatePasswordStrength(String password, String repeatPassword) {
        final String errorMessage;
        if (!isValidPassword(password)) {
            errorMessage = "Password must be 8 to 64 characters and include at least one letter and one number.";
        }
        else if (isBlank(repeatPassword)) {
            errorMessage = "Repeated password cannot be empty.";
        }
        else if (hasLeadingOrTrailingWhitespace(repeatPassword)) {
            errorMessage = "Repeated password cannot start or end with spaces.";
        }
        else if (!password.equals(repeatPassword)) {
            errorMessage = "Passwords don't match.";
        }
        else {
            errorMessage = null;
        }
        return errorMessage;
    }

    /**
     * Validates submitted security question information.
     */
    private String validateSecurityInformation(String securityQuestion, String securityAnswer) {
        final String errorMessage;
        if (isBlank(securityQuestion)) {
            errorMessage = "Security question cannot be empty.";
        }
        else if (isBlank(securityAnswer)) {
            errorMessage = "Security answer cannot be empty.";
        }
        else if (securityAnswer.length() > MAX_SECURITY_ANSWER_LENGTH) {
            errorMessage = "Security answer cannot be longer than 100 characters.";
        }
        else {
            errorMessage = null;
        }
        return errorMessage;
    }

    /**
     * Checks whether a username follows the signup rules.
     */
    private boolean isValidUsername(String username) {
        return username.length() >= MIN_USERNAME_LENGTH
                && username.length() <= MAX_USERNAME_LENGTH
                && username.matches(USERNAME_PATTERN);
    }

    /**
     * Checks whether a password follows the signup rules.
     */
    private boolean isValidPassword(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH
                && containsLetter(password)
                && containsNumber(password);
    }

    /**
     * Checks whether a password contains at least one letter.
     */
    private boolean containsLetter(String password) {
        boolean foundLetter = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLetter(password.charAt(i))) {
                foundLetter = true;
            }
        }
        return foundLetter;
    }

    /**
     * Checks whether a password contains at least one number.
     */
    private boolean containsNumber(String password) {
        boolean foundNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                foundNumber = true;
            }
        }
        return foundNumber;
    }

    /**
     * Checks whether text starts or ends with whitespace.
     */
    private boolean hasLeadingOrTrailingWhitespace(String value) {
        return value != null && !value.equals(value.trim());
    }

    /**
     * Removes leading and trailing whitespace from submitted text.
     */
    private String trimToEmpty(String value) {
        final String trimmedValue;
        if (value == null) {
            trimmedValue = "";
        }
        else {
            trimmedValue = value.trim();
        }
        return trimmedValue;
    }
}
