package use_case.change_display_name;

/**
 * Interactor for the change display name use case.
 */
public class ChangeDisplayNameInteractor implements ChangeDisplayNameInputBoundary {

    /** Maximum allowed display name length. */
    private static final int MAX_DISPLAY_NAME_LENGTH = 30;

    private final ChangeDisplayNameUserDataAccessInterface userDataAccessObject;
    private final ChangeDisplayNameOutputBoundary presenter;

    public ChangeDisplayNameInteractor(ChangeDisplayNameUserDataAccessInterface userDataAccessObject,
                                       ChangeDisplayNameOutputBoundary presenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void changeDisplayName(ChangeDisplayNameInputData inputData) {
        final String username = inputData.getUsername();
        final String newDisplayName = inputData.getNewDisplayName();

        if (!userDataAccessObject.existsByName(username)) {
            presenter.prepareFailView("No account found with that username.");
            return;
        }
        if (newDisplayName == null || newDisplayName.isEmpty()) {
            presenter.prepareFailView("Display name cannot be empty.");
            return;
        }
        if (newDisplayName.length() > MAX_DISPLAY_NAME_LENGTH) {
            presenter.prepareFailView("Display name must be less than "
                    + MAX_DISPLAY_NAME_LENGTH + " characters.");
            return;
        }
        userDataAccessObject.changeDisplayName(username, newDisplayName);
        presenter.prepareSuccessView(new ChangeDisplayNameOutputData(username, newDisplayName));
    }
}
