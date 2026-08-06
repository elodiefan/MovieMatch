package use_case.fetch_chat_history;

import java.util.ArrayList;

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

        if (messageDataAccessObject.chatExists(username, otherUsername)) {
            final ArrayList<String> displayText = messageDataAccessObject.getChatHistory(username, otherUsername);

            final FetchChatHistoryOutputData fetchChatHistoryOutputData = new FetchChatHistoryOutputData(displayText);
            userPresenter.prepareFetchChatHistorySuccessView(fetchChatHistoryOutputData);
        }
        else {
            System.out.println("No chat exists between the two users.");
        }
    }
}
