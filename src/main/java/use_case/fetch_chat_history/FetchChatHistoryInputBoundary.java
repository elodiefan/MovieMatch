package use_case.fetch_chat_history;

/**
 * Input boundary for actions related to fetching chat history.
 */

public interface FetchChatHistoryInputBoundary {

    /**
     * @param fetchChatHistoryInputData the input data
     */
    void execute(FetchChatHistoryInputData fetchChatHistoryInputData);
}
