package use_case.signup;

/** Input data for the Signup Use Case. */
public class SignupInputData {

    private final String username;
    private final String displayName;
    private final String password;
    private final String repeatPassword;
    private final String securityQuestion;
    private final String securityAnswer;

    /** Creates the input data needed to attempt a new account signup. */
    public SignupInputData(String username, String displayName, String password,
                           String repeatPassword, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    /** Returns the username requested for the new account. */
    String getUsername() {
        return username;
    }

    /** Returns the public display name for the new account. */
    String getDisplayName() {
        return displayName;
    }

    /** Returns the password entered by the user. */
    String getPassword() {
        return password;
    }

    /** Returns the repeated password entered for confirmation. */
    String getRepeatPassword() {
        return repeatPassword;
    }

    /** Returns the selected security question for account recovery. */
    String getSecurityQuestion() {
        return securityQuestion;
    }

    /** Returns the answer to the selected security question. */
    String getSecurityAnswer() {
        return securityAnswer;
    }
}
