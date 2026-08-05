package use_case.access_message_chat;

public class AccessMessageChatInteractor implements AccessMessageChatInputBoundary {

    private final AccessMessageChatUserDataAccessInterface userDataAccessObject;
    private final AccessMessageChatOutputBoundary userPresenter;

    public AccessMessageChatInteractor(AccessMessageChatUserDataAccessInterface accessMessageChatUserDataAccessInterface,
                                       AccessMessageChatOutputBoundary accessMessageChatOutputBoundary) {
        this.userDataAccessObject = accessMessageChatUserDataAccessInterface;
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
            final AccessMessageChatOutputData accessMessageChatOutputData = new AccessMessageChatOutputData(false, false);
            userPresenter.prepareFailView(accessMessageChatOutputData);
        }
        else {
            final AccessMessageChatOutputData accessMessageChatOutputData = new AccessMessageChatOutputData(true, false);
            userPresenter.prepareSuccessView(accessMessageChatOutputData);
        }
    }
}
