package use_case.send_message;

import java.util.Date;

/**
 * The Send Message Interactor.
 */
public class SendMessageInteractor extends SendMessageInputBoundary {

    private final SendMessageUserDataAccessInterface messageDataAccessObject;
    private final SendMessageOutputBoundary userPresenter;

    public SendMessageInteractor(SendMessageUserDataAccessInterface sendMessageUserDataAccessInterface,
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
        final String message = sendMessageInputData.getMessage();
        final Date date = sendMessageInputData.getDate();

        if (!messageDataAccessObject.chatExists(username, otherUsername)) {
            messageDataAccessObject.createChat(username, otherUsername, message, date);
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(username, message, false);
            userPresenter.prepareSuccessView(sendMessageOutputData);
        }
        else {
            messageDataAccessObject.addMessage(username, otherUsername, message, date);
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(username, message, false);
            userPresenter.prepareSuccessView(sendMessageOutputData);
        }
    }
}
