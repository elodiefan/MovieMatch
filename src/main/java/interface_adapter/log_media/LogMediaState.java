package interface_adapter.log_media;

/**
 * State for logging media to user lists.
 */
public class LogMediaState {
    /** The success message. */
    private String message = "";
    /** The error message. */
    private String error = "";

    /**
     * Returns the success message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the success message.
     */
    public void setMessage(final String inputMessage) {
        this.message = inputMessage;
    }

    /**
     * Returns the error message.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error message.
     */
    public void setError(final String inputError) {
        this.error = inputError;
    }
}
