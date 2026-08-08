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

    private final int commaStart = 10;
    private final int commaEnd = 12;

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
     * Executes the Fetch Chat History Use Case, fetches updated chat history.
     * @param username username of the current user
     * @param otherUsername username of the other user
     * @param currentChat the part of chat currently displayed
     */
    public void executeFetchUpdateChatHistory(String username, String otherUsername, String currentChat) {
        final StringBuilder prevMessages = new StringBuilder(currentChat);
        if (prevMessages.length() == 0) {
            final LocalDateTime date = null;
            final FetchChatHistoryInputData fetchChatHistoryInputData = new
                    FetchChatHistoryInputData(username, otherUsername, prevMessages, date);
            fetchChatHistoryInteractor.execute(fetchChatHistoryInputData);
        }
        else {
            final int size = currentChat.trim().length();
            final StringBuilder lastDate = new StringBuilder(currentChat.trim().substring(size - 21, size - 1));
            lastDate.replace(commaStart, commaEnd, "T");
            final LocalDateTime date = LocalDateTime.parse(lastDate);

            final FetchChatHistoryInputData fetchChatHistoryInputData = new
                    FetchChatHistoryInputData(username, otherUsername, prevMessages, date);
            fetchChatHistoryInteractor.execute(fetchChatHistoryInputData);
        }
    }

    /**
     * Switches view back to other account view.
     */
    public void switchToOtherAccountView() {
        viewManagerModel.switchView(otherAccountViewName);
    }
}
