package interface_adapter.home_page;

import interface_adapter.ViewManagerModel;
import interface_adapter.other_account.OtherAccountState;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountState;
import interface_adapter.personal_account.PersonalAccountViewModel;
import use_case.get_profile.GetProfileOutputBoundary;
import use_case.get_profile.GetProfileOutputData;

/**
 * The Presenter for the Home Page Use Case.
 */

public class HomePagePresenter implements GetProfileOutputBoundary {

    private ViewManagerModel viewManagerModel;
    private PersonalAccountViewModel personalAccountViewModel;
    private OtherAccountViewModel otherAccountViewModel;

    public HomePagePresenter(ViewManagerModel viewManagerModel,
                             PersonalAccountViewModel personalAccountViewModel,
                             OtherAccountViewModel otherAccountViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.personalAccountViewModel = personalAccountViewModel;
        this.otherAccountViewModel = otherAccountViewModel;
    }

    @Override
    public void switchToPersonalAccountView(GetProfileOutputData response) {
        final PersonalAccountState personalAccountState = personalAccountViewModel.getState();
        personalAccountState.setUsername(response.getUsername());
        personalAccountState.setDisplayName(response.getDisplayName());
        personalAccountViewModel.setState(personalAccountState);
        personalAccountViewModel.firePropertyChanged();

        viewManagerModel.setState(personalAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToOtherAccountView(GetProfileOutputData response) {
        final OtherAccountState otherAccountState = otherAccountViewModel.getState();
        otherAccountState.setUsername(response.getUsername());
        otherAccountState.setDisplayName(response.getDisplayName());
        otherAccountState.setBlocked(response.isBlocked());
        otherAccountState.setViewMessageError("");
        otherAccountViewModel.setState(otherAccountState);
        otherAccountViewModel.firePropertyChanged();

        viewManagerModel.setState(otherAccountViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
