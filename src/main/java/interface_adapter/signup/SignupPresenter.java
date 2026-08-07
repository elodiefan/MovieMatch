package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/** Presenter for the Signup Use Case. */
public class SignupPresenter implements SignupOutputBoundary {
    private static final String LOGIN_VIEW_NAME = "log in";

    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    /** Creates a signup presenter. */
    public SignupPresenter(SignupViewModel signupViewModel) {
        this(null, signupViewModel);
    }

    /** Creates a signup presenter that can update app navigation. */
    public SignupPresenter(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
    }

    /** Prepares the signup view after a successful signup. */
    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsername(outputData.getUsername());
        signupState.setDisplayName(outputData.getDisplayName());
        signupState.setPassword("");
        signupState.setRepeatPassword("");
        signupState.setSecurityAnswer("");
        signupState.setSignupError(null);
        signupViewModel.setState(signupState);
        signupViewModel.firePropertyChanged();
        switchToLoginView();
    }

    /** Prepares the signup view after a failed signup. */
    @Override
    public void prepareFailView(String errorMessage) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setSignupError(errorMessage);
        signupViewModel.setState(signupState);
        signupViewModel.firePropertyChanged();
    }

    /** Switches from the signup view to the login view. */
    @Override
    public void switchToLoginView() {
        if (viewManagerModel != null) {
            viewManagerModel.setState(LOGIN_VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        }
    }
}
