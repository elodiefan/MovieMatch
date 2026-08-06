package interface_adapter.messaging;

import interface_adapter.ViewManagerModel;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;

import java.util.Date;

/**
 * Controller for Messaging.
 */

public class MessagingController {

    private final ViewManagerModel viewManagerModel;
    private final SendMessageInputBoundary sendMessageInteractor;
    private final String otherAccountViewName;

    public MessagingController(ViewManagerModel viewManagerModel, SendMessageInputBoundary sendMessageInteractor,
                               String otherAccountViewName) {
        this.viewManagerModel = viewManagerModel;
        this.sendMessageInteractor = sendMessageInteractor;
        this.otherAccountViewName = otherAccountViewName;
    }

    /**
     * Executes the Send Message Use Case.
     * @param username username of current user
     * @param otherUsername username of other user want to communicate with
     * @param body content of message
     * @param timestamp time when message was sent
     */
    public void executeSendMessage(String username, String otherUsername, String body, Date timestamp) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(username, otherUsername,
                body, timestamp);
        sendMessageInteractor.execute(sendMessageInputData);
    }

    public void executeFetchChatHistory(String username, String otherUsername) {
        final
    }

    public void switchToOtherAccountView() {
        viewManagerModel.switchView(otherAccountViewName);
    }
}
