package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import interface_adapter.account.AccountController;
import interface_adapter.account.AccountPresenter;
import interface_adapter.account.AccountViewModel;
import interface_adapter.delete_account.DeleteAccountPresenter;
import interface_adapter.delete_account.DeleteAccountViewModel;
import interface_adapter.reset_password.ResetPasswordViewModel;
import interface_adapter.security_question.SecurityQuestionViewModel;
import entity.StandardUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
//import interface_adapter.logout.LogoutController;
//import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.account.AccountInputBoundary;
import use_case.account.AccountInteractor;
import use_case.account.AccountOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.DeleteAccountOutputBoundary;
//import use_case.logout.LogoutInputBoundary;
//import use_case.logout.LogoutInteractor;
//import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.AccountView;
import view.DeleteAccountView;
import view.HomePageView;
import view.LoginView;
import view.ResetPasswordView;
import view.SecurityQuestionView;
import view.SignupView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this homework; you can
//                  think about ways to refactor the code to resolve these if you decide
//                  to work with this as starter code for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new StandardUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    // private final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

    private AccountView accountView;
    private AccountViewModel accountViewModel;
    private DeleteAccountView deleteAccountView;
    private DeleteAccountViewModel deleteAccountViewModel;
    private HomePageView homePageView;
    private HomePageViewModel homePageViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private ResetPasswordView resetPasswordView;
    private ResetPasswordViewModel resetPasswordViewModel;
    private SecurityQuestionView securityQuestionView;
    private SecurityQuestionViewModel securityQuestionViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Account View to the application.
     * @return this builder
     */
    public AppBuilder addAccountView() {
        accountViewModel = new AccountViewModel();
        accountView = new AccountView(accountViewModel);
        cardPanel.add(accountView, accountView.getViewName());
        return this;
    }

    /**
     * Adds the Delete Account View to the application.
     * @return this builder
     */
    public AppBuilder addDeleteAccountView() {
        deleteAccountViewModel = new DeleteAccountViewModel();
        deleteAccountView = new DeleteAccountView(deleteAccountViewModel);
        cardPanel.add(deleteAccountView, deleteAccountView.getViewName());
        return this;
    }

    /**
     * Adds the Home Page View to the application.
     * @return this builder
     */
    public AppBuilder addHomePageView() {
        homePageViewModel = new HomePageViewModel();
        homePageView = new HomePageView(homePageViewModel);
        cardPanel.add(homePageView, homePageView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the Reset Password View to the application.
     * @return this builder
     */
    public AppBuilder addResetPasswordView() {
        resetPasswordViewModel = new ResetPasswordViewModel();
        resetPasswordView = new ResetPasswordView(resetPasswordViewModel);
        cardPanel.add(resetPasswordView, resetPasswordView.getViewName());
        return this;
    }

    /**
     * Adds the Security Question View to the application.
     * @return this builder
     */
    public AppBuilder addSecurityQuestionView() {
        securityQuestionViewModel = new SecurityQuestionViewModel();
        securityQuestionView = new SecurityQuestionView(securityQuestionViewModel);
        cardPanel.add(securityQuestionView, securityQuestionView.getViewName());
        return this;
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Account Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAccountUseCase() {
        final AccountOutputBoundary accountOutputBoundary = new AccountPresenter(viewManagerModel, accountViewModel,
                resetPasswordViewModel, deleteAccountViewModel);
        final AccountInputBoundary accountInteractor = new AccountInteractor(
                userDataAccessObject, accountOutputBoundary);

        final AccountController accountController = new AccountController(accountInteractor);
        accountView.setAccountController(accountController);
        return this;
    }

    /**
     * Adds the Delete Account Use Case to the application.
     * @return this builder
     */
    public AppBuilder addDeleteAccountUseCase() {
        final DeleteAccountOutputBoundary deleteAccountOutputBoundary = new DeleteAccountPresenter(viewManagerModel,
                homePageViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Home Page Use Case to the application.
     * @return this builder
     */
    public AppBuilder addHomePageUseCase() {
        final DeleteAccountOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homePageViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final DeleteAccountOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homePageViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Reset Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addResetPasswordUseCase() {
        final DeleteAccountOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homePageViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Security Question Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSecurityQuestionUseCase() {
        final DeleteAccountOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homePageViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
