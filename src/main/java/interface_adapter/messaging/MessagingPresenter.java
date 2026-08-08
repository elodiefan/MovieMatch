package interface_adapter.messaging;

import interface_adapter.ViewManagerModel;
import interface_adapter.other_account.OtherAccountViewModel;
import use_case.fetch_chat_history.FetchChatHistoryOutputBoundary;
import use_case.fetch_chat_history.FetchChatHistoryOutputData;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

/**
 * The Presenter for Messaging.
 */
public class MessagingPresenter implements SendMessageOutputBoundary, FetchChatHistoryOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final MessagingViewModel messagingViewModel;
    private final OtherAccountViewModel otherAccountViewModel;

    public MessagingPresenter(ViewManagerModel viewManagerModel, MessagingViewModel messagingViewModel,
                              OtherAccountViewModel otherAccountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.messagingViewModel = messagingViewModel;
        this.otherAccountViewModel = otherAccountViewModel;
    }

    @Override
    public void switchToOtherAccountView() {
        final MessagingState messagingState = messagingViewModel.getState();
        messagingState.setDisplayText("");
        messagingViewModel.setState(messagingState);
        messagingViewModel.firePropertyChanged();

        viewManagerModel.setState(otherAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSendMessageSuccessView(SendMessageOutputData response) {
        final MessagingState messagingState = messagingViewModel.getState();
        messagingViewModel.setState(messagingState);
        messagingViewModel.firePropertyChanged("sent message");
    }

    @Override
    public void prepareFetchChatHistorySuccessView(FetchChatHistoryOutputData response) {
        final MessagingState messagingState = messagingViewModel.getState();
        messagingState.setDisplayText(response.getDisplayText());
        messagingViewModel.setState(messagingState);
        messagingViewModel.firePropertyChanged("fetched chat history");
    }
}
