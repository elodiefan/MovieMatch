package use_case.recommendation;

/**
 * Signals that an adjuster could not produce a usable adjustment.
 */
public class ScoreAdjustmentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a message.
     *
     * @param message the message
     */
    public ScoreAdjustmentException(final String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping an underlying failure.
     *
     * @param message the message
     * @param cause the cause
     */
    public ScoreAdjustmentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
