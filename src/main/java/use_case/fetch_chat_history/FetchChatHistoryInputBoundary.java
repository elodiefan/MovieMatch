package use_case.fetch_chat_history;

/**
 * Input boundary for actions related to fetching chat history.
 */

public interface FetchChatHistoryInputBoundary {

    /**
     * Executes the fetch chat history use case.
     * @param fetchChatHistoryInputData the input data
     */
    void execute(FetchChatHistoryInputData fetchChatHistoryInputData);
}
