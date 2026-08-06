package interface_adapter.messaging;

import interface_adapter.ViewManagerModel;
import interface_adapter.delete_account.DeleteAccountState;
import interface_adapter.other_account.OtherAccountViewModel;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

/**
 * The Presenter for Messaging.
 */
public class MessagingPresenter implements SendMessageOutputBoundary {

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
        viewManagerModel.setState(otherAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSendMessageSuccessView(SendMessageOutputData response) {
        final MessagingState messagingState = messagingViewModel.getState();
        messagingState.setDisplayText(response.getDisplayText());
        messagingViewModel.setState(messagingState);
        messagingViewModel.firePropertyChanged("sent message");
    }
}
