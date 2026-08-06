package use_case.fetch_chat_history;

public class FetchChatHistoryInteractor implements FetchChatHistoryInputBoundary {

    private final FetchChatHistoryMessageDataAccessInterface messageDataAccessObject;
    private final FetchChatHistoryOutputBoundary userPresenter;

    public FetchChatHistoryInteractor(FetchChatHistoryMessageDataAccessInterface messageDataAccessObject,
                                      FetchChatHistoryOutputBoundary fetchChatHistoryOutputBoundary) {
        this.messageDataAccessObject = messageDataAccessObject;
        this.userPresenter = fetchChatHistoryOutputBoundary;
    }

    /**
     * Executes the fetch chat history use case.
     */
    @Override
    public void execute(FetchChatHistoryInputData fetchChatHistoryInputData) {
        final String username = fetchChatHistoryInputData.getUsername();
        final String otherUsername = fetchChatHistoryInputData.getOtherUsername();

        final String displayText = messageDataAccessObject.getChatHistory(username, otherUsername);

        final FetchChatHistoryOutputData fetchChatHistoryOutputData = new
                FetchChatHistoryOutputData(displayText, false);
        userPresenter.prepareFetchChatHistorySuccessView(fetchChatHistoryOutputData);
    }
}
