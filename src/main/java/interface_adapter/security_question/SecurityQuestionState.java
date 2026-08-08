package interface_adapter.security_question;

/**
 * The state backing the Security Question view.
 * <p>
 * A plain data holder (same style as {@code LoginState}). The presenter writes
 * to it and the view reads from it to decide what to display: the question, any
 * message, and whether the input should be disabled because of a lock-out.
 */
public class SecurityQuestionState {

    private String username = "";
    private String securityQuestion = "";
    private String answer = "";

    /**
     * A neutral/success message to show the user (e.g. "Answer correct").
     */
    private String message = "";

    /**
     * An error message (wrong answer, locked out, no account).
     */
    private String error = "";

    /**
     * True while the account is locked; the view uses this to disable inputs.
     */
    private boolean lockedOut = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isLockedOut() {
        return lockedOut;
    }

    public void setLockedOut(boolean lockedOut) {
        this.lockedOut = lockedOut;
    }
}
