package interface_adapter.account;

import use_case.account.AccountInputBoundary;
import use_case.account.AccountInputData;

/**
 * The controller for the Account Use Case.
 */
public class AccountController {

    private final AccountInputBoundary accountUseCaseInteractor;

    public AccountController(AccountInputBoundary accountUseCaseInteractor) {
        this.accountUseCaseInteractor = accountUseCaseInteractor;
    }

    /**
     * Executes the Account Use Case.
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        final AccountInputData accountInputData = new AccountInputData(
                username, password);

        accountUseCaseInteractor.execute(loginInputData);
    }
}
