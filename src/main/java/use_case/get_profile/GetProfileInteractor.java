package use_case.get_profile;

/**
 * Interactor for get user profile use case.
 */

public class GetProfileInteractor implements GetProfileInputBoundary {
    private final GetProfileUserDataAccessInterface userDataAccessObject;
    private final GetProfileOutputBoundary userPresenter;

    public GetProfileInteractor(GetProfileUserDataAccessInterface getProfileUserDataAccessInterface,
                                GetProfileOutputBoundary getProfileOutputBoundary) {
        this.userDataAccessObject = getProfileUserDataAccessInterface;
        this.userPresenter = getProfileOutputBoundary;
    }

    /**
     * Executes the get user profile use case.
     */
    @Override
    public void execute(GetProfileInputData getProfileInputData) {
        final String currentUsername = userDataAccessObject.getCurrentUsername();
        final String userDisplayName = userDataAccessObject.getDisplayName(getProfileInputData.getUsername());
        final GetProfileOutputData getProfileOutputData = new GetProfileOutputData(getProfileInputData.getUsername(),
                userDisplayName, false);
        if (getProfileInputData.getUsername().equals(currentUsername)) {
            userPresenter.switchToPersonalAccountView(getProfileOutputData);
        }
        else {
            userPresenter.switchToOtherAccountView(getProfileOutputData);
        }
    }
}
