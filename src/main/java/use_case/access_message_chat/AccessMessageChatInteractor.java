package use_case.access_message_chat;

public class AccessMessageChatInteractor implements AccessMessageChatInputBoundary {

    private final AccessMessageChatUserDataAccessInterface userDataAccessObject;
    private final AccessMessageChatMessageDataAccessInterface messageDataAccessObject;
    private final AccessMessageChatOutputBoundary userPresenter;

    public AccessMessageChatInteractor(AccessMessageChatUserDataAccessInterface userDataAccessInterface,
                                       AccessMessageChatMessageDataAccessInterface messageDataAccessInterface,
                                       AccessMessageChatOutputBoundary accessMessageChatOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.messageDataAccessObject = messageDataAccessInterface;
        this.userPresenter = accessMessageChatOutputBoundary;
    }

    /**
     * Executes the access message chat use case.
     * @param accessMessageChatInputData the input data
     */
    @Override
    public void execute(AccessMessageChatInputData accessMessageChatInputData) {
        final String otherUsername = accessMessageChatInputData.getOtherUsername();
        if (userDataAccessObject.canMessage(otherUsername)) {
            userPresenter.prepareAccessMessageChatFailView("Cannot message this user.");
        }
        else {
            final String currentUsername = userDataAccessObject.getCurrentUsername();

            final String displayText =
                    messageDataAccessObject.getNewMessages(currentUsername, otherUsername);

            final AccessMessageChatOutputData accessMessageChatOutputData = new AccessMessageChatOutputData(
                    true, currentUsername, otherUsername, displayText, false);
            userPresenter.prepareAccessMessageChatSuccessView(accessMessageChatOutputData);
        }
    }
}
