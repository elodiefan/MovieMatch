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
        final StringBuilder prevMessages = fetchChatHistoryInputData.getPrevMessages();
        if (messageDataAccessObject.chatExists(username, otherUsername)) {
            if (fetchChatHistoryInputData.getDate() == null) {
                final String newMessages = messageDataAccessObject.getNewMessages(username, otherUsername);
                prevMessages.append(newMessages);
            }
            else {
                final String newMessages = messageDataAccessObject.getNewMessages(username, otherUsername,
                        fetchChatHistoryInputData.getDate());
                prevMessages.append(newMessages);
            }
        }
        final FetchChatHistoryOutputData fetchChatHistoryOutputData = new
                FetchChatHistoryOutputData(prevMessages, false);
        userPresenter.prepareFetchChatHistorySuccessView(fetchChatHistoryOutputData);
    }
}
