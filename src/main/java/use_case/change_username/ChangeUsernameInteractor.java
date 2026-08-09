package use_case.change_username;

import use_case.signup.SignupInteractor;

/**
 * Interactor for the change display name use case.
 */
public class ChangeUsernameInteractor implements ChangeUsernameInputBoundary {

    /** Maximum allowed display name length. */
    private static final int MAX_DISPLAY_NAME_LENGTH = 30;

    private final ChangeUsernameUserDataAccessInterface userDataAccessObject;
    private final ChangeUsernameOutputBoundary presenter;
    private final SignupInteractor signupInteractor;

    public ChangeUsernameInteractor(ChangeUsernameUserDataAccessInterface userDataAccessObject,
                                    ChangeUsernameOutputBoundary presenter,
                                    SignupInteractor signupInteractor) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
        this.signupInteractor = signupInteractor;
    }

    @Override
    public void changeUsername(ChangeUsernameInputData inputData) {
        final String username = inputData.getUsername();
        final String newUsername = inputData.getNewUsername();
        final String displayName = inputData.getDisplayName();

        String validationCheck = signupInteractor.validateUsername(newUsername);
        if (validationCheck == null && userDataAccessObject.existsByName(newUsername)) {
            validationCheck = "Username already exists.";
            presenter.prepareFailView(validationCheck);
        }
        else if (validationCheck != null) {
            presenter.prepareFailView(validationCheck);
        }
        else {
            userDataAccessObject.changeUsername(username, newUsername);
            presenter.prepareSuccessView(new ChangeUsernameOutputData(username, newUsername, displayName));
        }
    }
}
