package use_case.account;

import entity.User;

/**
 * The Account Interactor.
 */
public class AccountInteractor implements AccountInputBoundary {
    private final AccountUserDataAccessInterface userDataAccessObject;
    private final AccountOutputBoundary accountPresenter;

    public AccountInteractor(AccountUserDataAccessInterface userDataAccessInterface,
                           AccountOutputBoundary accountOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.accountPresenter = accountOutputBoundary;
    }

    @Override
    public void execute(AccountInputData accountInputData) {
        final String username = accountInputData.getUsername();
        final String password = accountInputData.getPassword();
        if (!userDataAccessObject.existsByUsername(username)) {
            accountPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                accountPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataAccessObject.get(accountInputData.getUsername());

                userDataAccessObject.save(user);
                userDataAccessObject.setCurrentUsername(user.getUsername());
                final AccountOutputData accountOutputData = new AccountOutputData(user.getUsername(), false);
                accountPresenter.prepareSuccessView(accountOutputData);
            }
        }
    }

    /**
     * Switches from account view to reset password view.
     */
    @Override
    public void switchToResetPasswordView() {
        accountPresenter.switchToResetPasswordView();
    }

    /**
     * Switches from account view to delete account view.
     */
    @Override
    public void switchToDeleteAccountView() {
        accountPresenter.switchToDeleteAccountView();
    }

}
