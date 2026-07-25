package use_case.change_password;

import entity.User;
import entity.UserFactory;
import use_case.login.LoginOutputData;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        final String username = changePasswordInputData.getUsername();
        final String password = changePasswordInputData.getPassword();
        final String securityAnswer = changePasswordInputData.getSecurityAnswer();

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataAccessObject.get(loginInputData.getUsername());

                userDataAccessObject.save(user);
                userDataAccessObject.setCurrentUsername(user.getName());
                final LoginOutputData loginOutputData = new LoginOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }

        final User user = userFactory.create(changePasswordInputData.getUsername(),
                changePasswordInputData.getPassword());
        userDataAccessObject.changePassword(user);

        final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(user.getName(),
                false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
