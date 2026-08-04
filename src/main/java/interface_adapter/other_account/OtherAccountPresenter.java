package interface_adapter.other_account;

import interface_adapter.ViewManagerModel;
import interface_adapter.delete_account.DeleteAccountState;
import interface_adapter.delete_account.DeleteAccountViewModel;
import interface_adapter.reset_password.ResetPasswordViewModel;
import use_case.account.AccountOutputBoundary;
import use_case.account.AccountOutputData;

/**
 * The Presenter for the Account Use Case.
 */
public class OtherAccountPresenter implements AccountOutputBoundary {

    private final OtherAccountViewModel otherAccountViewModel;
    private final ViewManagerModel viewManagerModel;
    // private final ReviewsViewModel reviewsViewModel;

    public OtherAccountPresenter(ViewManagerModel viewManagerModel,
                                 OtherAccountViewModel otherAccountViewModel,
                                 // ReviewsViewModel reviewsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.otherAccountViewModel = otherAccountViewModel;
      //  this.reviewsViewModel = reviewsViewModel;
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

//TODO: switch to message view
}
