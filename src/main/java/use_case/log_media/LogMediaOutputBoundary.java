package use_case.log_media;

/** Output boundary for logging media to user lists. */
public interface LogMediaOutputBoundary {

    /** Prepares the success view after a media item is logged. */
    void prepareSuccessView(LogMediaOutputData outputData);

    /** Prepares the failure view after media logging fails. */
    void prepareFailView(String error);
}
