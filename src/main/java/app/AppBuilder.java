package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryLockoutTracker;
import data_access.MongoUserDataAccessObject;
import data_access.UserDataAccessObject;
import interface_adapter.account.AccountController;
import interface_adapter.account.AccountPresenter;
import interface_adapter.account.AccountViewModel;
import interface_adapter.comments.CommentsViewModel;
import interface_adapter.delete_account.DeleteAccountController;
import interface_adapter.delete_account.DeleteAccountPresenter;
import interface_adapter.delete_account.DeleteAccountViewModel;
import interface_adapter.home_page.HomePageController;
import interface_adapter.home_page.HomePagePresenter;
import interface_adapter.media_reviews.MediaReviewsViewModel;
import interface_adapter.reset_password.ResetPasswordController;
import interface_adapter.reset_password.ResetPasswordPresenter;
import interface_adapter.reset_password.ResetPasswordViewModel;
import interface_adapter.security_question.SecurityQuestionController;
import interface_adapter.security_question.SecurityQuestionPresenter;
import interface_adapter.security_question.SecurityQuestionViewModel;
import entity.StandardUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.user_reviews.UserReviewsViewModel;
import use_case.account.AccountInputBoundary;
import use_case.account.AccountInteractor;
import use_case.account.AccountOutputBoundary;
import use_case.delete_account.DeleteAccountInputBoundary;
import use_case.delete_account.DeleteAccountInteractor;
import use_case.delete_account.DeleteAccountOutputBoundary;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.get_profile.GetProfileInteractor;
import use_case.get_profile.GetProfileOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.reset_password.ResetPasswordInputBoundary;
import use_case.reset_password.ResetPasswordInteractor;
import use_case.reset_password.ResetPasswordOutputBoundary;
import use_case.security_question.SecurityQuestionInputBoundary;
import use_case.security_question.SecurityQuestionInteractor;
import use_case.security_question.SecurityQuestionOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new StandardUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Needs a mongo.properties file in the project root; see the MongoDB guide.
    // Swap to InMemoryUserDataAccessObject to run without a network.
    private final UserDataAccessObject userDataAccessObject = new MongoUserDataAccessObject();

    // Counts failed security answers and holds lock-outs. One shared instance, so
    // every attempt on the same account is counted together.
    private final InMemoryLockoutTracker lockoutTracker = new InMemoryLockoutTracker();

    private AccountView accountView;
    private AccountViewModel accountViewModel;
    private DeleteAccountView deleteAccountView;
    private DeleteAccountViewModel deleteAccountViewModel;
    private HomePageView homePageView;
    private HomePageViewModel homePageViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private LogoutConfirmView logoutView;
    private LogoutViewModel logoutViewModel;
    private CommentsPanel commentsPanel;
    private CommentsViewModel commentsViewModel;
    private MediaReviewsPanel mediaReviewsPanel;
    private MediaReviewsViewModel mediaReviewsViewModel;
    private ResetPasswordView resetPasswordView;
    private ResetPasswordViewModel resetPasswordViewModel;
    private MyReviewsView myReviewsView;
    private UserReviewsViewModel userReviewsViewModel;
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
        loginView = new LoginView(loginViewModel, viewManagerModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    // TODO: For Yidan -> Fix error on line 154. LogoutConfirmView takes in a String
    //  but should maybe take in a view model.
    /**
     * Adds the Logout View to the application.
     * @return this builder
     */
    public AppBuilder addLogoutView() {
        logoutViewModel = new LogoutViewModel();
        logoutView = new LogoutConfirmView(logoutViewModel);
        cardPanel.add(logoutView, logoutView.getViewName());
        return this;
    }

    /**
     * Adds the Reset Password View to the application.
     * @return this builder
     */
    public AppBuilder addResetPasswordView() {
        resetPasswordViewModel = new ResetPasswordViewModel();
        resetPasswordView = new ResetPasswordView(resetPasswordViewModel, viewManagerModel);
        cardPanel.add(resetPasswordView, resetPasswordView.getViewName());
        return this;
    }

    /**
     * Adds review-related views and panels to the application.
     * @return this builder
     */
    public AppBuilder addReviewsView() {
        userReviewsViewModel = new UserReviewsViewModel();
        mediaReviewsViewModel = new MediaReviewsViewModel();
        commentsViewModel = new CommentsViewModel();

        myReviewsView = new MyReviewsView(userReviewsViewModel);
        mediaReviewsPanel = new MediaReviewsPanel(mediaReviewsViewModel,
                commentsViewModel);
        commentsPanel = new CommentsPanel(commentsViewModel);

        cardPanel.add(myReviewsView, myReviewsView.getViewName());
        cardPanel.add(mediaReviewsPanel, mediaReviewsViewModel.getViewName());
        cardPanel.add(commentsPanel, commentsViewModel.getViewName());
        return this;
    }

    /**
     * Adds the Security Question View to the application.
     * @return this builder
     */
    public AppBuilder addSecurityQuestionView() {
        securityQuestionViewModel = new SecurityQuestionViewModel();
        securityQuestionView = new SecurityQuestionView(securityQuestionViewModel, viewManagerModel);
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
//        final AccountOutputBoundary accountOutputBoundary = new AccountPresenter(
//                viewManagerModel, accountViewModel, reviewsViewModel, null,
//                resetPasswordViewModel, deleteAccountViewModel);
        final AccountOutputBoundary accountOutputBoundary = new AccountPresenter(
                viewManagerModel, accountViewModel, resetPasswordViewModel, deleteAccountViewModel);
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
                deleteAccountViewModel, signupViewModel, accountViewModel);
        final DeleteAccountInputBoundary deleteAccountInteractor = new DeleteAccountInteractor(
                userDataAccessObject, deleteAccountOutputBoundary, userFactory);

        final DeleteAccountController deleteAccountController = new DeleteAccountController(deleteAccountInteractor);
        deleteAccountView.setDeleteAccountController(deleteAccountController);
        return this;
    }

    // TODO: For Yidan/Kiersten -> Implement search view files.
    /**
     * Adds the Home Page Use Case to the application.
     * @return this builder
     */
    public AppBuilder addHomePageUseCase() {
//        final HomePageOutputBoundary homePageOutputBoundary = new HomePagePresenter(viewManagerModel,
//                homePageViewModel, searchViewModel, accountViewModel);
        final GetProfileOutputBoundary homePageOutputBoundary = new HomePagePresenter(viewManagerModel,
                homePageViewModel, accountViewModel);
        final GetProfileInputBoundary homePageInteractor = new GetProfileInteractor(
                userDataAccessObject, homePageOutputBoundary);

        final HomePageController homePageController = new HomePageController(homePageInteractor);
        homePageView.setHomePageController(homePageController);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                homePageViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

//    // TODO: For Elodie -> Implement reviews files.
//    /**
//     * Adds the Reviews Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addReviewsUseCase() {
//        final ReviewsOutputBoundary reviewsOutputBoundary = new ReviewsPresenter(viewManagerModel, reviewsViewModel);
//        final ReviewsInputBoundary reviewsInteractor = new ReviewsInteractor(
//                userDataAccessObject, reviewsOutputBoundary);
//
//        final ReviewsController reviewsController = new ReviewsController(reviewsInteractor);
//        reviewsView.setReviewsController(reviewsController);
//        return this;
//    }

    /**
     * Adds the Reset Password Use Case to the application.
     * <p>
     * A PasswordResetCompletedHandler is just "what happens once the new password
     * is saved" - the presenter calls it so it does not need to know which screen
     * comes next. Here that means sending the user back to the login screen so
     * they can sign in with the password they just chose.
     * @return this builder
     */
    public AppBuilder addResetPasswordUseCase() {
        final ResetPasswordOutputBoundary resetPasswordOutputBoundary = new ResetPasswordPresenter(
                resetPasswordViewModel,
                username -> {
                    viewManagerModel.setState(LoginViewModel.VIEW_NAME);
                    viewManagerModel.firePropertyChanged();
                });
        final ResetPasswordInputBoundary resetPasswordInteractor = new ResetPasswordInteractor(
                userDataAccessObject, resetPasswordOutputBoundary);
        final ResetPasswordController resetPasswordController = new ResetPasswordController(resetPasswordInteractor);
        resetPasswordView.setResetPasswordController(resetPasswordController);
        return this;
    }

    /**
     * Adds the Security Question Use Case to the application.
     * <p>
     * A LockoutTracker records failed answers per account and locks it after too
     * many wrong tries; {@link InMemoryLockoutTracker} keeps that in memory, so it
     * resets when the app restarts.
     * @return this builder
     */
    public AppBuilder addSecurityQuestionUseCase() {
        final SecurityQuestionOutputBoundary securityQuestionOutputBoundary = new SecurityQuestionPresenter(
                securityQuestionViewModel,
                resetPasswordViewModel, viewManagerModel);
        final SecurityQuestionInputBoundary securityQuestionInteractor = new SecurityQuestionInteractor(
                userDataAccessObject, securityQuestionOutputBoundary, lockoutTracker);
        final SecurityQuestionController securityQuestionController = new SecurityQuestionController(
                securityQuestionInteractor);
        securityQuestionView.setSecurityQuestionController(securityQuestionController);
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

        final SignupController signupController = new SignupController(userSignupInteractor);
        signupView.setSignupController(signupController);
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
