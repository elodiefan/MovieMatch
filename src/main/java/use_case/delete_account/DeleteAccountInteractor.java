package use_case.delete_account;

import entity.User;
import entity.UserFactory;

/**
 * Interactor for Delete Account Use Case.
 */

public class DeleteAccountInteractor implements DeleteAccountInputBoundary {
    private final DeleteAccountUserDataAccessInterface userDataAccessObject;
    private final DeleteAccountOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public DeleteAccountInteractor(DeleteAccountUserDataAccessInterface deleteAccountDataAccessInterface,
                                   DeleteAccountOutputBoundary deleteAccountOutputBoundary,
                                   UserFactory userFactory) {
        this.userDataAccessObject = deleteAccountDataAccessInterface;
        this.userPresenter = deleteAccountOutputBoundary;
        this.userFactory = userFactory;
    }

    /**
     * Executes the Delete Account Use Case.
     * @param deleteAccountInputData the user's input info
     */
    @Override
    public void execute(DeleteAccountInputData deleteAccountInputData) {
        final String username = deleteAccountInputData.getUsername();
        final String password = deleteAccountInputData.getPassword();
        final String securityAnswer = deleteAccountInputData.getSecurityAnswer();

        if (!userDataAccessObject.getCurrentSecurityAnswer().equals(securityAnswer)) {
            userPresenter.prepareFailView("Incorrect security answer for \"" + username + "\".");
        }
        else {
            final User user = userFactory.create(username, password);
            userDataAccessObject.setCurrentUsername(null);
            final DeleteAccountOutputData deleteAccountOutputData = new DeleteAccountOutputData(username, false);
            userDataAccessObject.deleteAccount(user);
            userPresenter.prepareSuccessView(deleteAccountOutputData);
        }
    }

    /**
     * Switches from delete account view to signup view.
     */
    @Override
    public void switchToSignupView() {
        userPresenter.switchToSignupView();
    }
}
