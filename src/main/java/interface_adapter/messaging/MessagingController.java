package interface_adapter.messaging;

import java.time.LocalDateTime;

import interface_adapter.ViewManagerModel;
import use_case.fetch_chat_history.FetchChatHistoryInputBoundary;
import use_case.fetch_chat_history.FetchChatHistoryInputData;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;

/**
 * Controller for Messaging.
 */

public class MessagingController {

    private final ViewManagerModel viewManagerModel;
    private final SendMessageInputBoundary sendMessageInteractor;
    private final FetchChatHistoryInputBoundary fetchChatHistoryInteractor;
    private final String otherAccountViewName;

    public MessagingController(ViewManagerModel viewManagerModel, SendMessageInputBoundary sendMessageInteractor,
                               FetchChatHistoryInputBoundary fetchChatHistoryInteractor, String otherAccountViewName) {
        this.viewManagerModel = viewManagerModel;
        this.sendMessageInteractor = sendMessageInteractor;
        this.fetchChatHistoryInteractor = fetchChatHistoryInteractor;
        this.otherAccountViewName = otherAccountViewName;
    }

    /**
     * Executes the Send Message Use Case.
     * @param username username of current user
     * @param otherUsername username of other user want to communicate with
     * @param body content of message
     * @param timestamp time when message was sent
     */
    public void executeSendMessage(String username, String otherUsername, String body, LocalDateTime timestamp) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(username, otherUsername,
                body, timestamp);
        sendMessageInteractor.execute(sendMessageInputData);
    }

    /**
     * Executes the Fetch Chat History Use Case, fetches entire chat history.
     * @param username username of the current user
     * @param otherUsername username of the other user
     */
    public void executeFetchAllChatHistory(String username, String otherUsername) {
        final StringBuilder prevMessages = new StringBuilder();
        final FetchChatHistoryInputData fetchChatHistoryInputData = new
                FetchChatHistoryInputData(username, otherUsername, prevMessages, null);
        fetchChatHistoryInteractor.execute(fetchChatHistoryInputData);
    }

    /**
     * Executes the Fetch Chat History Use Case, fetches updated chat history.
     * @param username username of the current user
     * @param otherUsername username of the other user
     * @param currentChat the part of chat currently displayed
     * @param lastTimeSent timestamp of last message currently shown
     */
    public void executeFetchUpdateChatHistory(String username, String otherUsername, String currentChat,
                                              LocalDateTime lastTimeSent) {
        final StringBuilder prevMessages = new StringBuilder(currentChat);
        final FetchChatHistoryInputData fetchChatHistoryInputData = new
                FetchChatHistoryInputData(username, otherUsername, prevMessages, lastTimeSent);
        fetchChatHistoryInteractor.execute(fetchChatHistoryInputData);
    }

    /**
     * Switches view back to other account view.
     */
    public void switchToOtherAccountView() {
        viewManagerModel.switchView(otherAccountViewName);
    }
}
