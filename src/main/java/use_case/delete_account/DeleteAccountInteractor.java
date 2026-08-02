package use_case.delete_account;

import java.util.Timer;
import java.util.TimerTask;

import entity.User;
import entity.UserFactory;

/**
 * Interactor for Delete Account Use Case.
 */

public class DeleteAccountInteractor implements DeleteAccountInputBoundary {
    private static final int INCORRECT_LIMIT = 3;

    private final DeleteAccountUserDataAccessInterface userDataAccessObject;
    private final DeleteAccountOutputBoundary userPresenter;
    private final UserFactory userFactory;

    private boolean isLockedOut;

    private int incorrectCount;

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
        final String displayName = deleteAccountInputData.getDisplayName();
        final String password = deleteAccountInputData.getPassword();
        final String securityQuestion = deleteAccountInputData.getSecurityQuestion();
        final String securityAnswer = deleteAccountInputData.getSecurityAnswer();

        if (!isLockedOut) {
            if (!userDataAccessObject.getCurrentSecurityAnswer().equals(securityAnswer)) {
                userPresenter.prepareFailView("Incorrect security answer.");
                incorrectCount++;
                if (incorrectCount >= INCORRECT_LIMIT) {
                    isLockedOut = true;
                    userPresenter.prepareFailView("Answered incorrectly 3 times. Try again in 1 minute.");
                    lockOut();
                }
            }
            else {
                incorrectCount = 0;
                final User user = userFactory.create(username, displayName, password, securityQuestion, securityAnswer);
                userDataAccessObject.setCurrentUsername(null);
                final DeleteAccountOutputData deleteAccountOutputData = new DeleteAccountOutputData(username, false);
                userDataAccessObject.deleteAccount(user);
                userPresenter.prepareSuccessView(deleteAccountOutputData);
            }
        }
    }

    private void lockOut() {
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            private int count = 1;
            @Override
            public void run() {
                incorrectCount = 0;
                isLockedOut = false;
                count--;
                if (count <= 0) {
                    timer.cancel();
                }
            }
        };
        final int lockOutTime = 60000;
        timer.schedule(task, lockOutTime);
    }

    /**
     * Switches from delete account view to account view.
     */
    @Override
    public void switchToAccountView() {
        userPresenter.switchToAccountView();
    }
}