package interface_adapter.other_account;

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
    //private final GetWatchlistInputBoundary getWatchlistInteractor;
    //private final GetWatchHistoryInputBoundary getWatchHistoryInteractor;
    //private final GetReviewsInputBoundary getReviewsInteractor;
    private final AccessMessageChatInputBoundary accessMessageChatInteractor;

    public OtherAccountController(BlockUserInputBoundary blockUserInteractor, AccessMessageChatInputBoundary accessMessageChatInteractor) {
        this.blockUserInteractor = blockUserInteractor;
        this.accessMessageChatInteractor =  accessMessageChatInteractor;
    }

    /**
     * Executes block user use case.
     * @param otherUsername the username of the other user
     */
    public void executeBlockUser(String otherUsername) {
        final BlockUserInputData blockUserInputData = new BlockUserInputData(otherUsername);
        blockUserInteractor.execute(blockUserInputData);
    }

    // TODO: get user watch list use case

    // TODO: get user watch history use case

    // TODO: get user reviews use case

    public void goToMessages(String otherUsername) {
        final AccessMessageChatInputData accessMessageChatInputData = new AccessMessageChatInputData(otherUsername);
        accessMessageChatInteractor.execute(accessMessageChatInputData);
    }

    // TODO: switch to search view
    public void switchToSearchView() {

    }
}
