package interface_adapter.other_account;

import interface_adapter.ViewManagerModel;
import interface_adapter.get_lists.GetListsController;
import use_case.access_message_chat.AccessMessageChatInputBoundary;
import use_case.access_message_chat.AccessMessageChatInputData;
import use_case.block_user.BlockUserInputBoundary;
import use_case.block_user.BlockUserInputData;
//import use_case.get_watchlist.GetWatchlistInputBoundary;
//import use_case.get_watch_history.GetWatchHistoryInputBoundary;
//import use_case.get_reviews.GetReviews.InputBoundary;
//import use_case.send_message.SendMessageInputBoundary;

/**
 * The controller for the Account Use Case.
 */
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

    /**
     * Executes block user use case.
     * @param otherUsername the username of the other user
     */
    public void executeBlockUser(String otherUsername) {
        final BlockUserInputData blockUserInputData = new BlockUserInputData(otherUsername);
        blockUserInteractor.execute(blockUserInputData);
    }

    /**
     * Executes the get watchlist view use case.
     * @param username the username of the user.
     * @param displayName the display name of the user.
     */
    public void switchToWatchlistView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchlistUseCase(username, displayName);
    }

    /**
     * Executes the get watch history view use case.
     * @param username the username of the user.
     * @param displayName the display name of the user.
     */
    public void switchToWatchHistoryView(String username, String displayName) {
        viewManagerModel.switchView(getListsViewName);
        getListsController.executeWatchHistoryUseCase(username, displayName);
    }

    // TODO: get user reviews use case

    /**
     * Switches view to chatroom with other user.
     * @param otherUsername username of the other user
     */
    public void goToMessages(String otherUsername) {
        final AccessMessageChatInputData accessMessageChatInputData = new AccessMessageChatInputData(otherUsername);
        accessMessageChatInteractor.execute(accessMessageChatInputData);
    }

    // TODO: switch to search view
    public void switchToSearchView() {

    }
}
