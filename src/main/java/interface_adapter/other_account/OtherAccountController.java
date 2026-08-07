package interface_adapter.other_account;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_lists.GetListsController;
import interface_adapter.search_user.SearchUserViewModel;
import use_case.access_message_chat.AccessMessageChatInputBoundary;
import use_case.access_message_chat.AccessMessageChatInputData;
import use_case.block_user.BlockUserInputBoundary;
import use_case.block_user.BlockUserInputData;
//import use_case.get_watchlist.GetWatchlistInputBoundary;
//import use_case.get_watch_history.GetWatchHistoryInputBoundary;
//import use_case.get_reviews.GetReviews.InputBoundary;
//import use_case.send_message.SendMessageInputBoundary;

/** The controller for the Account Use Case. */
public class OtherAccountController {

    private final BlockUserInputBoundary blockUserInteractor;
    private final GetListsController getListsController;
    private final ViewManagerModel viewManagerModel;
    private final String getListsViewName = "view lists";
    //private final GetWatchlistInputBoundary getWatchlistInteractor;
    //private final GetWatchHistoryInputBoundary getWatchHistoryInteractor;
    //private final GetReviewsInputBoundary getReviewsInteractor;
    private final AccessMessageChatInputBoundary accessMessageChatInteractor;

    public OtherAccountController(ViewManagerModel viewManagerModel,
                                  BlockUserInputBoundary blockUserInteractor,
                                  GetListsController getListsController,
                                  AccessMessageChatInputBoundary accessMessageChatInteractor) {
        this.viewManagerModel = viewManagerModel;
        this.blockUserInteractor = blockUserInteractor;
        this.getListsController = getListsController;
        this.accessMessageChatInteractor = accessMessageChatInteractor;
    }

    /** Executes block user use case. */
    public void executeBlockUser(String otherUsername) {
        final BlockUserInputData blockUserInputData = new BlockUserInputData(otherUsername);
        blockUserInteractor.execute(blockUserInputData);
    }

    /** Executes the get watchlist view use case. */
    public void switchToWatchlistView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchlistUseCase(username, displayName);
    }

    /** Executes the get watch history view use case. */
    public void switchToWatchHistoryView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchHistoryUseCase(username, displayName);
    }

    // TODO: get user reviews use case

    /** Opens the chat with this user, if messaging has been wired up yet. */
    public void goToMessages(String otherUsername) {
        // Messaging is still being built, so this may not be connected yet.
        // Without the guard the button throws instead of doing nothing.
        if (accessMessageChatInteractor != null) {
            final AccessMessageChatInputData accessMessageChatInputData =
                    new AccessMessageChatInputData(otherUsername);
            accessMessageChatInteractor.execute(accessMessageChatInputData);
        }
    }

    /** Returns to the screen this profile was opened from. */
    public void switchToSearchView() {
        viewManagerModel.switchView(SearchUserViewModel.VIEW_NAME);
    }

    /** Says whether the message button can do anything yet. */
    public boolean isMessagingAvailable() {
        return accessMessageChatInteractor != null;
    }
}
