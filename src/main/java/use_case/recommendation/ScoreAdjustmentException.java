package use_case.recommendation;

/**
 * Signals that an adjuster could not produce a usable adjustment.
 * <p>
 * Adjusters that call an outside service throw this when the key is missing,
 * the call fails, or the reply cannot be parsed. It exists so callers can catch
 * exactly this and fall back to no adjustment, rather than catching every
 * runtime exception and hiding real bugs along with it.
 */
public class ScoreAdjustmentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a message.
     *
     * @param message what went wrong
     */
    public ScoreAdjustmentException(final String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping an underlying failure.
     *
     * @param message what went wrong
     * @param cause the failure underneath
     */
    public ScoreAdjustmentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
