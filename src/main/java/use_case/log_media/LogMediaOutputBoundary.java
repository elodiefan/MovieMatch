package use_case.log_media;

/**
 * Output boundary for logging media to user lists.
 */
public interface LogMediaOutputBoundary {

    /**
     * Prepares the success view after a media item is logged.
     * @param outputData the logging result
     */
    void prepareSuccessView(LogMediaOutputData outputData);

    /**
     * Prepares the failure view after media logging fails.
     * @param error the error message
     */
    void prepareFailView(String error);
}
