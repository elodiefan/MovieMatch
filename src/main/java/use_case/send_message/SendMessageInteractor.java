package use_case.send_message;

import java.time.LocalDateTime;

import entity.Message;

/**
 * The Send Message Interactor.
 */
public class SendMessageInteractor implements SendMessageInputBoundary {

    private final SendMessageMessageDataAccessInterface messageDataAccessObject;
    private final SendMessageOutputBoundary userPresenter;

    public SendMessageInteractor(SendMessageMessageDataAccessInterface sendMessageUserDataAccessInterface,
                                 SendMessageOutputBoundary sendMessageOutputBoundary) {
        this.messageDataAccessObject = sendMessageUserDataAccessInterface;
        this.userPresenter = sendMessageOutputBoundary;
    }

    /**
     * Executes the Send Message Use Case.
     * @param sendMessageInputData the user's input info
     */
    @Override
    public void execute(SendMessageInputData sendMessageInputData) {
        final String username = sendMessageInputData.getUsername();
        final String otherUsername = sendMessageInputData.getOtherUsername();
        final String body = sendMessageInputData.getBody();
        final LocalDateTime date = sendMessageInputData.getDate();
        if (!body.trim().equals("")) {
            final Message message = new Message(username, otherUsername, body, date);
//            if (!messageDataAccessObject.chatExists(username, otherUsername)) {
//                messageDataAccessObject.createChat(username, otherUsername);
//            }
            messageDataAccessObject.addMessage(message);
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(username, body, false);
            userPresenter.prepareSendMessageSuccessView(sendMessageOutputData);
        }
    }

    @Override
    public void switchToOtherAccountView() {
        userPresenter.switchToOtherAccountView();
    }
}
