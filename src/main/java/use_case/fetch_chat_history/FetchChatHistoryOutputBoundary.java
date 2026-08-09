package use_case.fetch_chat_history;

/**
 * The output boundary for the fetch chat history use case.
 */

public interface FetchChatHistoryOutputBoundary {
    /**
     * Prepares the success view for the fetch chat history use case.
     * @param outputData the output data
     */
    void prepareFetchChatHistorySuccessView(FetchChatHistoryOutputData outputData);
}
