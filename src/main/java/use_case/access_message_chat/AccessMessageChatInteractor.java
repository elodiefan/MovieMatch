package use_case.access_message_chat;

import java.util.ArrayList;

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
            userPresenter.prepareAccessMessageChatFailView("Cannot message this user.");
        }
        else {
            final String currentUsername = userDataAccessObject.getCurrentUsername();
            final ArrayList<String> displayText = messageDataAccessObject.getChatHistory();

            final AccessMessageChatOutputData accessMessageChatOutputData = new AccessMessageChatOutputData(
                    true, currentUsername, otherUsername, displayText, false);
            userPresenter.prepareAccessMessageChatSuccessView(accessMessageChatOutputData);
        }
    }
}
