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
     */
    @Override
    public void execute(String inputOtherUsername) {
        final AccessMessageChatInputData accessMessageChatInputData =
                new AccessMessageChatInputData(inputOtherUsername);
        final String otherUsername = accessMessageChatInputData.getOtherUsername();
        if (userDataAccessObject.canMessage(otherUsername)) {
            userPresenter.prepareAccessMessageChatFailView("Cannot message this user.");
        }
        else {
            final AccessMessageChatOutputData accessMessageChatOutputData = new AccessMessageChatOutputData(true, false);
            userPresenter.prepareAccessMessageChatSuccessView(accessMessageChatOutputData.canViewChat());
        }
    }
}
