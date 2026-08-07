package interface_adapter.other_account;

import interface_adapter.ViewManagerModel;
import use_case.access_message_chat.AccessMessageChatOutputBoundary;
import use_case.access_message_chat.AccessMessageChatOutputData;
import use_case.block_user.BlockUserOutputBoundary;
import use_case.block_user.BlockUserOutputData;

/**
 * The Presenter for Other Account.
 */
public class OtherAccountPresenter implements BlockUserOutputBoundary { //, AccessMessageChatOutputBoundary {

    private final OtherAccountViewModel otherAccountViewModel;
    private final ViewManagerModel viewManagerModel;

    public OtherAccountPresenter(ViewManagerModel viewManagerModel,
                                 OtherAccountViewModel otherAccountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.otherAccountViewModel = otherAccountViewModel;
    }


//    @Override
//    public void switchToReviewsView() {
//        final ReviewsState reviewsState = reviewsViewModel.getState();
//        // gets the current state object from reviewsViewModel and stores it in local variable reviewsState
//        reviewsState.setUsername(accountViewModel.getState().getUsername());
//        // so that before switching views, it can copy the acc username into reviewsState
//        // so if the acc page is showing user "elodie", the reviewsState now also knows about that
//        reviewsViewModel.setState(reviewsState);
//        // puts the updated reviewsState back into reviewViewModel
//        reviewsViewModel.firePropertyChanged();
//        // notify reviewsViewModel that the state has changed, so that reviewsView can later refresh
//        viewManagerModel.setState(reviewsViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
//    }

    @Override
    public void prepareBlockSuccessView(BlockUserOutputData response) {
        final OtherAccountState otherAccountState = otherAccountViewModel.getState();
        otherAccountState.setBlocked(response.isOnBlockList());
        otherAccountViewModel.setState(otherAccountState);
        otherAccountViewModel.firePropertyChanged("changed block state");
    }

//    @Override
//    public void prepareAccessMessageChatSuccessView(AccessMessageChatOutputData response) {
//        final MessagingState messagingState = messagingViewModel.getState();
//        this.messagingViewModel.setstate(messagingState);
//        this.messagingViewModel.firePropertyChanged();
//
//        this.viewManagerModel.setState(messagingViewModel.getViewName());
//        this.viewManagerModel.firePropertyChanged();
//    }

//    @Override
//    public void prepareAccessMessageChatFailView(String error) {
//        final OtherAccountState otherAccountState = otherAccountViewModel.getState();
//        otherAccountState.setViewMessageError(error);
//        otherAccountViewModel.firePropertyChanged("cannot message");
//    }

//TODO: switch to message view
}
