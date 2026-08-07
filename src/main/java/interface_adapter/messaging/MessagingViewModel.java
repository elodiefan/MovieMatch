package interface_adapter.messaging;

import interface_adapter.ViewModel;

public class MessagingViewModel extends ViewModel<MessagingState> {

    public static final String VIEW_NAME = "chat";
    public static final String TITLE_LABEL = "Chat View";

    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String REFRESH = "Refresh";

    public MessagingViewModel() {
        super("chat");
        setState(new MessagingState());
    }
}
