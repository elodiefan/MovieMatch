package use_case.account;

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

    /**
     * Switches from account view to reset password view.
     */
    @Override
    public void switchToLoginView() {
        accountPresenter.switchToLoginView();
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
