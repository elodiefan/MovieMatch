package use_case.send_message;

/**
 * The output boundary for the Send Message Use Case.
 */
public interface SendMessageOutputBoundary {
    /**
     * Prepares the success view for the Block User Use case.
     * @param outputData the output data
     */
    void prepareSendMessageSuccessView(SendMessageOutputData outputData);

    /**
     * Prepares to switch view back to other user account.
     */
    void switchToOtherAccountView();
}
