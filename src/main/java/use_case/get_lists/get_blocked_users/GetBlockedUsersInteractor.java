package use_case.get_lists.get_blocked_users;

/**
 * The Blocked Users View Interactor.
 */
public class GetBlockedUsersInteractor implements GetBlockedUsersInputBoundary {

    private final GetBlockedUsersUserDataAccessInterface userDataAccessObject;
    private final GetBlockedUsersOutputBoundary getListsPresenter;

    public GetBlockedUsersInteractor(GetBlockedUsersUserDataAccessInterface userDataAccessInterface,
                                     GetBlockedUsersOutputBoundary getBlockedUsersOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.getListsPresenter = getBlockedUsersOutputBoundary;
    }

    @Override
    public void execute(GetBlockedUsersInputData getListsInputData) {
        final String username = getListsInputData.getUsername();
        final String displayName = getListsInputData.getDisplayName();
        final String blockedUsers = userDataAccessObject.getLists(username).getBlockedUsers();
        final GetBlockedUsersOutputData getBlockedUsersOutputData = new GetBlockedUsersOutputData(username,
                displayName, blockedUsers);
        getListsPresenter.prepareSuccessView(getBlockedUsersOutputData);
    }

    /**
     * Switches from list view to account view.
     *
     * @param getListsInputData the get lists input data
     */
    @Override
    public void switchToAccountView(GetBlockedUsersInputData getListsInputData) {
        if (userDataAccessObject.getCurrentUsername().equals(getListsInputData.getUsername())) {
            getListsPresenter.switchToPersonalAccountView();
        }
        else {
            getListsPresenter.switchToOtherAccountView();
        }
    }
}
