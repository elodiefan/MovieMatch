package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import views.*;

import database.*;

import interface_adapter.change_display_name.ChangeDisplayNameController;
import interface_adapter.change_display_name.ChangeDisplayNamePresenter;
import interface_adapter.change_display_name.ChangeDisplayNameViewModel;
import interface_adapter.change_username.ChangeUsernameController;
import interface_adapter.change_username.ChangeUsernamePresenter;
import interface_adapter.change_username.ChangeUsernameViewModel;
import interface_adapter.comments.CommentsController;
import interface_adapter.comments.CommentsPresenter;
import interface_adapter.comments.CommentsViewModel;
import interface_adapter.delete_account.DeleteAccountController;
import interface_adapter.delete_account.DeleteAccountPresenter;
import interface_adapter.delete_account.DeleteAccountViewModel;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterPresenter;
import interface_adapter.get_lists.GetListsController;
import interface_adapter.get_lists.GetListsPresenter;
import interface_adapter.get_lists.GetListsViewModel;
import interface_adapter.home_page.HomePageController;
import interface_adapter.home_page.HomePagePresenter;
import interface_adapter.log_media.LogMediaController;
import interface_adapter.log_media.LogMediaPresenter;
import interface_adapter.log_media.LogMediaViewModel;
import interface_adapter.media_detail.MediaDetailController;
import interface_adapter.media_detail.MediaDetailPresenter;
import interface_adapter.media_detail.MediaDetailViewModel;
import interface_adapter.media_reviews.MediaReviewsController;
import interface_adapter.media_reviews.MediaReviewsPresenter;
import interface_adapter.media_reviews.MediaReviewsViewModel;
import interface_adapter.other_account.OtherAccountController;
import interface_adapter.other_account.OtherAccountPresenter;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountController;
import interface_adapter.personal_account.PersonalAccountPresenter;
import interface_adapter.personal_account.PersonalAccountViewModel;
import interface_adapter.reset_password.ResetPasswordController;
import interface_adapter.reset_password.ResetPasswordPresenter;
import interface_adapter.reset_password.ResetPasswordViewModel;
import use_case.change_display_name.ChangeDisplayNameInputBoundary;
import use_case.change_display_name.ChangeDisplayNameInteractor;
import use_case.change_display_name.ChangeDisplayNameOutputBoundary;
import use_case.change_username.ChangeUsernameInputBoundary;
import use_case.change_username.ChangeUsernameInteractor;
import use_case.change_username.ChangeUsernameOutputBoundary;
import interface_adapter.search_user.SearchUserViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.recommendation.RecommendationViewModel;
import interface_adapter.search_result.SearchResultViewModel;
import interface_adapter.settings.SettingsController;
import interface_adapter.settings.SettingsPresenter;
import interface_adapter.settings.SettingsViewModel;
import use_case.settings.SettingsInputBoundary;
import use_case.settings.SettingsInteractor;
import use_case.settings.SettingsOutputBoundary;
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
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.user_reviews.UserReviewsController;
import interface_adapter.user_reviews.UserReviewsPresenter;
import interface_adapter.user_reviews.UserReviewsViewModel;
import use_case.create_comment.CreateCommentInteractor;
import use_case.delete_comment.DeleteCommentInteractor;
import use_case.get_review_comments.GetReviewCommentsInteractor;
import use_case.get_user_comments.GetUserCommentsInputBoundary;
import use_case.get_user_comments.GetUserCommentsInteractor;
import use_case.like_comment.LikeCommentInteractor;
import use_case.unlike_comment.UnlikeCommentInteractor;
import use_case.block_user.BlockUserInputBoundary;
import use_case.block_user.BlockUserInteractor;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.get_blocked_users.GetBlockedUsersInputBoundary;
import use_case.get_blocked_users.GetBlockedUsersInteractor;
import use_case.get_blocked_users.GetBlockedUsersOutputBoundary;
import use_case.get_watch_history.GetWatchHistoryInputBoundary;
import use_case.get_watch_history.GetWatchHistoryInteractor;
import use_case.get_watch_history.GetWatchHistoryOutputBoundary;
import use_case.get_watchlist.GetWatchlistOutputBoundary;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.get_profile.GetProfileInteractor;
import use_case.get_profile.GetProfileOutputBoundary;
import use_case.delete_account.DeleteAccountInputBoundary;
import use_case.delete_account.DeleteAccountInteractor;
import use_case.delete_account.DeleteAccountOutputBoundary;
import use_case.get_watchlist.GetWatchlistInputBoundary;
import use_case.get_watchlist.GetWatchlistInteractor;
import use_case.get_security_question.GetSecurityQuestionInputBoundary;
import use_case.get_security_question.GetSecurityQuestionInteractor;
import use_case.get_security_question.GetSecurityQuestionOutputBoundary;
import use_case.media_detail.MediaDetailInputBoundary;
import use_case.media_detail.MediaDetailInteractor;
import use_case.media_detail.MediaDetailOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.log_media.LogMediaInputBoundary;
import use_case.log_media.LogMediaInteractor;
import use_case.log_media.LogMediaOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.reset_password.ResetPasswordInputBoundary;
import use_case.reset_password.ResetPasswordInteractor;
import use_case.reset_password.ResetPasswordOutputBoundary;
import use_case.create_review.CreateReviewInteractor;
import use_case.delete_review.DeleteReviewInputBoundary;
import use_case.delete_review.DeleteReviewInteractor;
import use_case.edit_review.EditReviewInputBoundary;
import use_case.edit_review.EditReviewInteractor;
import use_case.get_media_reviews.GetMediaReviewsInteractor;
import use_case.get_user_reviews.GetUserReviewsInputBoundary;
import use_case.get_user_reviews.GetUserReviewsInteractor;
import use_case.get_user_reviews.GetUserReviewsOutputBoundary;
import use_case.like_review.LikeReviewInputBoundary;
import use_case.like_review.LikeReviewInteractor;
import use_case.unlike_review.UnlikeReviewInputBoundary;
import use_case.unlike_review.UnlikeReviewInteractor;
import use_case.security_question.SecurityQuestionInputBoundary;
import use_case.security_question.SecurityQuestionInteractor;
import use_case.security_question.SecurityQuestionOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 *
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {

    private static final String WINDOW_TITLE = "MovieMatch";

    /**
     * Comfortable size on a laptop screen; the window is still resizable.
     */
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 640;

    /**
     * Below this the denser screens start clipping.
     */
    private static final int MIN_WIDTH = 640;
    private static final int MIN_HEIGHT = 480;

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new StandardUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Needs a mongo.properties file in the project root; see the MongoDB guide.
    // Swap to InMemoryUserDataAccessObject to run without a network.
    private final UserDataAccessObject userDataAccessObject = new MongoUserDataAccessObject();
    private final MongoReviewDataAccessObject reviewDataAccessObject =
            new MongoReviewDataAccessObject();
    private final MongoCommentDataAccessObject commentDataAccessObject =
            new MongoCommentDataAccessObject();

    // Counts failed security answers and holds lock-outs. One shared instance, so
    // every attempt on the same account is counted together.
    private final InMemoryLockoutTracker lockoutTracker = new InMemoryLockoutTracker();
    private final TmdbReviewDataAccessObject tmdbReviewDataAccessObject =
            new TmdbReviewDataAccessObject(new TmdbApiClient());
    private final CombinedMediaReviewDataAccessObject
            mediaReviewDataAccessObject =
            new CombinedMediaReviewDataAccessObject(tmdbReviewDataAccessObject,
                    reviewDataAccessObject);

    private ChangeDisplayNameView changeDisplayNameView;
    private ChangeDisplayNameViewModel changeDisplayNameViewModel;
    private ChangeUsernameView changeUsernameView;
    private ChangeUsernameViewModel changeUsernameViewModel;
    private DeleteAccountView deleteAccountView;
    private DeleteAccountViewModel deleteAccountViewModel;
    private GetListsView getListsView;
    private GetListsViewModel getListsViewModel;
    private HomePageView homePageView;
    private HomePageViewModel homePageViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private LogoutConfirmView logoutView;
    private LogoutViewModel logoutViewModel;
    private OtherAccountView otherAccountView;
    private OtherAccountViewModel otherAccountViewModel;
    private PersonalAccountView personalAccountView;
    private PersonalAccountViewModel personalAccountViewModel;
    private ResetPasswordView resetPasswordView;
    private ResetPasswordViewModel resetPasswordViewModel;
    private MyReviewsView userReviewsView;
    private UserReviewsViewModel userReviewsViewModel;
    private SecurityQuestionView securityQuestionView;
    private SecurityQuestionViewModel securityQuestionViewModel;
    private SearchUserView searchUserView;
    private SearchUserViewModel searchUserViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private SearchResultView searchResultView;
    private SearchResultViewModel searchResultViewModel;
    private SettingsView settingsView;
    private SettingsViewModel settingsViewModel;
    private RecommendationView recommendationView;
    private HomeRecommendationsPanel homeRecommendationsPanel;

    /**
     * Shared by the settings and recommendation use cases: one writes the
     * content preference, the other reads it.
     */
    private final InMemoryContentPreferences contentPreferences = new InMemoryContentPreferences();

    /**
     * One per screen, because the strip and the full list show different amounts.
     */
    private RecommendationViewModel homeStripRecommendationViewModel;
    private RecommendationViewModel detailedRecommendationViewModel;
    private MediaDetailView mediaDetailView;
    private MediaDetailViewModel mediaDetailViewModel;
    private MediaReviewsViewModel mediaReviewsViewModel;
    private CommentsViewModel commentsViewModel;
    private LogMediaViewModel logMediaViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Change Display Name View to the application.
     * @return this builder
     */
    public AppBuilder addChangeDisplayNameView() {
        changeDisplayNameViewModel = new ChangeDisplayNameViewModel();
        changeDisplayNameView = new ChangeDisplayNameView(changeDisplayNameViewModel,
                personalAccountViewModel, viewManagerModel);
        cardPanel.add(changeDisplayNameView, changeDisplayNameView.getViewName());
        return this;
    }

    /**
     * Adds the Change Username View to the application.
     * @return this builder
     */
    public AppBuilder addChangeUsernameView() {
        changeUsernameViewModel = new ChangeUsernameViewModel();
        changeUsernameView = new ChangeUsernameView(changeUsernameViewModel,
                personalAccountViewModel, viewManagerModel);
        cardPanel.add(changeUsernameView, changeUsernameView.getViewName());
        return this;
    }

    /**
     * Adds the Delete Account View to the application.
     *
     * @return the add delete account view
     */
    public AppBuilder addDeleteAccountView() {
        deleteAccountViewModel = new DeleteAccountViewModel();
        deleteAccountView = new DeleteAccountView(deleteAccountViewModel);
        cardPanel.add(deleteAccountView, deleteAccountView.getViewName());
        return this;
    }

    /**
     * Adds the Get Lists View to the application.
     *
     * @return the add get lists view
     */
    public AppBuilder addGetListsView() {
        getListsViewModel = new GetListsViewModel();
        getListsView = new GetListsView(getListsViewModel);
        cardPanel.add(getListsView, getListsView.getViewName());
        return this;
    }

    /**
     * Adds the Home Page View to the application.
     *
     * @return the add home page view
     */
    public AppBuilder addHomePageView() {
        homePageViewModel = new HomePageViewModel();
        homePageView = new HomePageView(homePageViewModel);
        cardPanel.add(homePageView, homePageView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     *
     * @return the add login view
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel, viewManagerModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the Logout View to the application.
     *
     * @return the add logout view
     */
    public AppBuilder addLogoutView() {
        logoutViewModel = new LogoutViewModel();
        logoutView = new LogoutConfirmView(logoutViewModel, viewManagerModel,
                PersonalAccountViewModel.VIEW_NAME);
        cardPanel.add(logoutView, logoutView.getViewName());
        return this;
    }

    /**
     * Adds the Other Account View to the application.
     *
     * @return the add other account view
     */
    public AppBuilder addOtherAccountView() {
        otherAccountViewModel = new OtherAccountViewModel();
        otherAccountView = new OtherAccountView(otherAccountViewModel);
        cardPanel.add(otherAccountView, otherAccountView.getViewName());
        return this;
    }

    /**
     * Adds the Other Account Use Case to the application.
     *
     * Without this the view is registered but its controller is never set, so
     * every button on another user's profile throws instead of doing anything.
     * Messaging is passed as null because that use case is still being built;
     * the controller and view both check before using it.
     *
     * @return the add other account use case
     */
    public AppBuilder addOtherAccountUseCase() {
        // The presenter accepts a reviews view model but currently discards it,
        // so this is inert until that part of the presenter is finished.
        final OtherAccountPresenter otherAccountPresenter = new OtherAccountPresenter(viewManagerModel,
                otherAccountViewModel);
        final BlockUserInputBoundary blockUserInteractor = new BlockUserInteractor(userDataAccessObject,
                otherAccountPresenter);

        final OtherAccountController otherAccountController = new OtherAccountController(viewManagerModel,
                blockUserInteractor, createGetListsController(), null);
        otherAccountView.setOtherAccountController(otherAccountController);
        return this;
    }

    /**
     * Adds the Personal Account View to the application.
     *
     * @return the add personal account view
     */
    public AppBuilder addPersonalAccountView() {
        personalAccountViewModel = new PersonalAccountViewModel();
        personalAccountView = new PersonalAccountView(personalAccountViewModel);
        cardPanel.add(personalAccountView, personalAccountView.getViewName());
        return this;
    }

    /**
     * Adds the Reset Password View to the application.
     *
     * @return the add reset password view
     */
    public AppBuilder addResetPasswordView() {
        resetPasswordViewModel = new ResetPasswordViewModel();
        resetPasswordView = new ResetPasswordView(resetPasswordViewModel);
        resetPasswordView.setBackHandler(
                () -> viewManagerModel.switchView(LoginViewModel.VIEW_NAME));
        cardPanel.add(resetPasswordView, resetPasswordView.getViewName());
        return this;
    }

    /**
     * Adds the My Reviews View to the application.
     *
     * @return the add user reviews view
     */
    public AppBuilder addUserReviewsView() {
        userReviewsViewModel = new UserReviewsViewModel();
        userReviewsView = new MyReviewsView(userReviewsViewModel);
        // The view builds a back button and exposes it, but nothing was listening,
        // so this screen had no way out.
        userReviewsView.getBackButton().addActionListener(
                event -> viewManagerModel.switchView(PersonalAccountViewModel.VIEW_NAME));
        cardPanel.add(userReviewsView, userReviewsView.getViewName());
        return this;
    }

    /**
     * Adds the Security Question View to the application.
     *
     * @return the add security question view
     */
    public AppBuilder addSecurityQuestionView() {
        securityQuestionViewModel = new SecurityQuestionViewModel();
        securityQuestionView = new SecurityQuestionView(securityQuestionViewModel);
        securityQuestionView.setBackHandler(
                () -> viewManagerModel.switchView(LoginViewModel.VIEW_NAME));
        cardPanel.add(securityQuestionView, securityQuestionView.getViewName());
        return this;
    }

    /**
     * Adds the Search User View to the application.
     *
     * @return the add search user view
     */
    public AppBuilder addSearchUserView() {
        searchUserViewModel = new SearchUserViewModel();
        searchUserView = new SearchUserView(searchUserViewModel);
        cardPanel.add(searchUserView, searchUserView.getViewName());
        return this;
    }

    /**
     * Adds the Signup View to the application.
     *
     * @return the add signup view
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Change Display Name Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangeDisplayNameUseCase() {
        final ChangeDisplayNameOutputBoundary changeDisplayNameOutputBoundary = new ChangeDisplayNamePresenter(
                viewManagerModel, changeDisplayNameViewModel);
        final ChangeDisplayNameInputBoundary changeDisplayNameInteractor = new ChangeDisplayNameInteractor(
                userDataAccessObject, changeDisplayNameOutputBoundary);
        final ChangeDisplayNameController changeDisplayNameController = new ChangeDisplayNameController(
                changeDisplayNameInteractor, viewManagerModel, personalAccountViewModel);
        changeDisplayNameView.setChangeDisplayNameController(changeDisplayNameController);
        return this;
    }

    /**
     * Adds the Change Username Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangeUsernameUseCase() {

        final ChangeUsernameOutputBoundary changeUsernameOutputBoundary = new ChangeUsernamePresenter(
                viewManagerModel, changeUsernameViewModel);
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(signupViewModel);
        final ChangeUsernameInputBoundary changeUsernameInteractor = new ChangeUsernameInteractor(
                userDataAccessObject, changeUsernameOutputBoundary, new SignupInteractor(userDataAccessObject, signupOutputBoundary, userFactory));
        final ChangeUsernameController changeUsernameController = new ChangeUsernameController(
                changeUsernameInteractor, viewManagerModel, personalAccountViewModel);
        changeUsernameView.setChangeUsernameController(changeUsernameController);
        return this;
    }

    /**
     * Adds the Delete Account Use Case to the application.
     *
     * @return the add delete account use case
     */
    public AppBuilder addDeleteAccountUseCase() {
        final DeleteAccountOutputBoundary deleteAccountOutputBoundary = new DeleteAccountPresenter(viewManagerModel,
                deleteAccountViewModel, signupViewModel, personalAccountViewModel);
        final DeleteAccountInputBoundary deleteAccountInteractor = new DeleteAccountInteractor(
                userDataAccessObject, deleteAccountOutputBoundary, userFactory);

        final DeleteAccountController deleteAccountController = new DeleteAccountController(deleteAccountInteractor);
        deleteAccountView.setDeleteAccountController(deleteAccountController);
        return this;
    }

    /**
     * Adds the Get Watchlist Use Case to the application.
     *
     * @return the add get watchlist use case
     */
    public AppBuilder addGetWatchlistUseCase() {
        final GetWatchlistOutputBoundary getWatchlistOutputBoundary = new GetListsPresenter(viewManagerModel,
                getListsViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetWatchlistInputBoundary getWatchlistInteractor = new GetWatchlistInteractor(
                userDataAccessObject, getWatchlistOutputBoundary);

        final GetListsController getListsController = createGetListsController();
        getListsView.setGetListsController(getListsController);
        return this;
    }

    /**
     * Adds the Get Watch History Use Case to the application.
     *
     * @return the add get watch history use case
     */
    public AppBuilder addGetWatchHistoryUseCase() {
        final GetWatchHistoryOutputBoundary getWatchHistoryOutputBoundary = new GetListsPresenter(viewManagerModel,
                getListsViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetWatchHistoryInputBoundary getWatchHistoryInteractor = new GetWatchHistoryInteractor(
                userDataAccessObject, getWatchHistoryOutputBoundary);

        final GetListsController getListsController = new GetListsController(getWatchHistoryInteractor);
        getListsView.setGetListsController(getListsController);
        return this;
    }

    /**
     * Adds the Get Blocked Users Use Case to the application.
     *
     * @return the add get blocked users use case
     */
    public AppBuilder addGetBlockedUsersUseCase() {
        final GetBlockedUsersOutputBoundary getBlockedUsersOutputBoundary = new GetListsPresenter(viewManagerModel,
                getListsViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetBlockedUsersInputBoundary getBlockedUsersInteractor = new GetBlockedUsersInteractor(
                userDataAccessObject, getBlockedUsersOutputBoundary);

        final GetListsController getListsController = new GetListsController(getBlockedUsersInteractor);
        getListsView.setGetListsController(getListsController);
        return this;
    }

//    // TODO: For Yidan/Kiersten -> Implement search view files.
    /**
     * Adds the Home Page Use Case to the application.
     *
     * @return the add get profile use case
     */
    public AppBuilder addGetProfileUseCase() {
        final GetProfileOutputBoundary userPresenter = new HomePagePresenter(viewManagerModel,
                personalAccountViewModel, otherAccountViewModel);
        final GetProfileInputBoundary getProfileInteractor = new GetProfileInteractor(userDataAccessObject,
                userPresenter);

        final HomePageController homePageController =
                new HomePageController(
                        getProfileInteractor,
                        viewManagerModel,
                        SearchViewModel.VIEW_NAME,
                        SearchUserViewModel.VIEW_NAME,
                        SettingsViewModel.VIEW_NAME
                );
        homePageView.setHomePageController(homePageController);
        return this;
    }

    /**
     * Adds the Search User Use Case to the application.
     *
     * The assembly lives in SearchUserUseCaseFactory, following the
     * convention Yidan set with SearchUseCaseFactory.
     *
     * @return the add search user use case
     */
    public AppBuilder addSearchUserUseCase() {
        final GetProfileOutputBoundary getProfileOutputBoundary = new HomePagePresenter(viewManagerModel,
                personalAccountViewModel, otherAccountViewModel);
        final GetProfileInputBoundary getProfileInteractor = new GetProfileInteractor(userDataAccessObject,
                getProfileOutputBoundary);

        SearchUserUseCaseFactory.create(
                viewManagerModel,
                searchUserViewModel,
                searchUserView,
                userDataAccessObject,
                getProfileInteractor,
                homePageViewModel.getViewName());
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     *
     * @return the add login use case
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

    /**
     * Adds the Personal Account Use Case to the application.
     *
     * @return the add personal account use case
     */
    public AppBuilder addPersonalAccountUseCase() {
        final GetProfileOutputBoundary getProfileOutputBoundary = new HomePagePresenter(viewManagerModel,
                personalAccountViewModel, otherAccountViewModel);

        final GetListsController getListsController = createGetListsController();
        final GetSecurityQuestionOutputBoundary getSecurityQuestionOutputBoundary = new PersonalAccountPresenter(viewManagerModel,
                personalAccountViewModel, resetPasswordViewModel, deleteAccountViewModel);
        final GetSecurityQuestionInputBoundary getSecurityQuestionInteractor = new GetSecurityQuestionInteractor(userDataAccessObject,
                getSecurityQuestionOutputBoundary);

        final PersonalAccountController personalAccountController = new PersonalAccountController(viewManagerModel,
                getSecurityQuestionInteractor,
                getListsController,
                changeDisplayNameViewModel,
                changeUsernameViewModel,
                logoutViewModel,
                resetPasswordViewModel,
                homePageViewModel.getViewName(),
                getListsViewModel.getViewName(),
                userReviewsViewModel);

        personalAccountView.setPersonalAccountController(personalAccountController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     *
     * Every piece of this use case already existed but was never assembled, so the
     * Log Out button on the personal account page did nothing.
     *
     * @return the add logout use case
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                new LoggedInViewModel(), loginViewModel);
        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject,
                logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        logoutView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the User Reviews Use Case to the application.
     *
     * @return the add user reviews use case
     */
    public AppBuilder addUserReviewsUseCase() {
        final UserReviewsPresenter userReviewsPresenter = new UserReviewsPresenter(userReviewsViewModel);
        final GetUserReviewsOutputBoundary userReviewsOutputBoundary = userReviewsPresenter;
        final GetUserReviewsInputBoundary userReviewsInteractor = new GetUserReviewsInteractor(reviewDataAccessObject,
                userReviewsOutputBoundary);

        final EditReviewInputBoundary editReviewsInteractor = new EditReviewInteractor(reviewDataAccessObject,
                userReviewsPresenter);
        final DeleteReviewInputBoundary deleteReviewsInteractor = new DeleteReviewInteractor(reviewDataAccessObject,
                userReviewsPresenter);
        final LikeReviewInputBoundary likeReviewsInteractor = new LikeReviewInteractor(reviewDataAccessObject,
                userReviewsPresenter);
        final UnlikeReviewInputBoundary unlikeReviewsInteractor = new UnlikeReviewInteractor(reviewDataAccessObject,
                userReviewsPresenter);
        final GetUserCommentsInputBoundary userCommentsInteractor = new GetUserCommentsInteractor(commentDataAccessObject,
                reviewDataAccessObject, userReviewsPresenter);

        final UserReviewsController userReviewsController = new UserReviewsController(userReviewsInteractor,
                editReviewsInteractor,
                deleteReviewsInteractor,
                likeReviewsInteractor,
                unlikeReviewsInteractor,
                userCommentsInteractor);
        userReviewsView.setUserReviewsController(userReviewsController);
        return this;
    }

    /**
     * Adds the Reset Password Use Case to the application.
     *
     * A PasswordResetCompletedHandler is just "what happens once the new password
     * is saved" — the presenter calls it so it does not need to know which screen
     * comes next. Here that means sending the user back to the login screen so
     * they can sign in with the password they just chose.
     *
     * @return the add reset password use case
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
     *
     * A LockoutTracker records failed answers per account and locks it after too
     * many wrong tries; InMemoryLockoutTracker keeps that in memory, so it
     * resets when the app restarts.
     *
     * @return the add security question use case
     */
    public AppBuilder addSecurityQuestionUseCase() {
        final SecurityQuestionOutputBoundary securityQuestionOutputBoundary = new SecurityQuestionPresenter(
                securityQuestionViewModel,
                resetPasswordViewModel,
                () -> viewManagerModel.switchView(ResetPasswordViewModel.VIEW_NAME),
                () -> viewManagerModel.switchView(SecurityQuestionViewModel.VIEW_NAME));
        final SecurityQuestionInputBoundary securityQuestionInteractor = new SecurityQuestionInteractor(
                userDataAccessObject, securityQuestionOutputBoundary, lockoutTracker);
        final SecurityQuestionController securityQuestionController = new SecurityQuestionController(
                securityQuestionInteractor);
        securityQuestionView.setSecurityQuestionController(securityQuestionController);
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     *
     * @return the add signup use case
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary =
                new SignupPresenter(signupViewModel,
                        () -> viewManagerModel.switchView(LoginViewModel.VIEW_NAME));
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController signupController = new SignupController(userSignupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    /**
     * Adds the Search View to the application.
     *
     * @return the add search view
     */
    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel, viewManagerModel, HomePageViewModel.VIEW_NAME);

        cardPanel.add(
                searchView,
                searchView.getViewName()
        );

        return this;
    }

    /**
     * Adds the Search Result View to the application.
     *
     * @return the add recommendation view
     */
    public AppBuilder addRecommendationView() {
        homeStripRecommendationViewModel = new RecommendationViewModel();
        detailedRecommendationViewModel = new RecommendationViewModel();

        homeRecommendationsPanel = new HomeRecommendationsPanel(homeStripRecommendationViewModel);
        recommendationView = new RecommendationView(detailedRecommendationViewModel);

        homePageView.setRecommendationsPanel(homeRecommendationsPanel);

        // The two screens load separately, so asking for the full list has to
        // start it fetching as well as switch to it.
        homeRecommendationsPanel.setSeeAllHandler(recommendationView::loadFor);

        cardPanel.add(recommendationView, recommendationView.getViewName());
        return this;
    }

    /**
     * Adds the Recommendation Use Case to the application.
     * @return this builder
     */
    public AppBuilder addRecommendationUseCase() {
        RecommendationUseCaseFactory.create(
                viewManagerModel,
                homeStripRecommendationViewModel,
                detailedRecommendationViewModel,
                homeRecommendationsPanel,
                recommendationView,
                userDataAccessObject,
                reviewDataAccessObject,
                contentPreferences);
        return this;
    }

    /**
     * Adds the Settings View to the application.
     * @return this builder
     */
    public AppBuilder addSettingsView() {
        settingsViewModel = new SettingsViewModel();
        settingsView = new SettingsView(settingsViewModel);
        // A theme change has to reach every screen, not just this one.
        settingsView.setAppearanceRoot(cardPanel);
        cardPanel.add(settingsView, settingsView.getViewName());
        return this;
    }

    /**
     * Adds the Settings Use Case to the application.
     *
     * @return the add settings use case
     */
    public AppBuilder addSettingsUseCase() {
        final SettingsOutputBoundary settingsOutputBoundary = new SettingsPresenter(settingsViewModel);
        // The same object the recommendation use case reads from, so turning
        // the checkbox off is what the next set of suggestions is built on.
        final SettingsInputBoundary settingsInteractor =
                new SettingsInteractor(settingsOutputBoundary, contentPreferences);

        final SettingsController settingsController = new SettingsController(settingsInteractor);
        settingsView.setSettingsController(settingsController);
        settingsView.setBackHandler(
                () -> viewManagerModel.switchView(HomePageViewModel.VIEW_NAME));
        return this;
    }

    /**
     * Adds the Search Result View to the application.
     *
     * @return the add search result view
     */
    public AppBuilder addSearchResultView() {
        searchResultViewModel = new SearchResultViewModel();
        searchResultView = new SearchResultView(searchResultViewModel, viewManagerModel,
                SearchViewModel.VIEW_NAME);
        cardPanel.add(searchResultView, searchResultView.getViewName());
        return this;
    }

    /**
     * Adds the Search Use Case to the application.
     *
     * @return the add search use case
     */
    public AppBuilder addSearchUseCase() {
        SearchUseCaseFactory.create(
                viewManagerModel,
                searchViewModel,
                searchResultViewModel,
                searchView,
                searchResultView
        );
        return this;
    }

    /**
     * Adds the Filter Use Case to the application.
     *
     * @return the add filter use case
     */
    public AppBuilder addFilterUseCase() {
        final FilterOutputBoundary filterPresenter =
                new FilterPresenter(searchResultViewModel);

        final FilterInputBoundary filterInteractor =
                new FilterInteractor(filterPresenter);

        final FilterController filterController =
                new FilterController(filterInteractor);

        searchResultView.setFilterController(filterController);

        return this;
    }

    /**
     * Adds the Media Detail View to the application.
     *
     * @return the add media detail view
     */
    public AppBuilder addMediaDetailView() {
        mediaDetailViewModel = new MediaDetailViewModel();
        mediaReviewsViewModel = new MediaReviewsViewModel();
        commentsViewModel = new CommentsViewModel();
        logMediaViewModel = new LogMediaViewModel();

        mediaDetailView = new MediaDetailView(
                mediaDetailViewModel,
                mediaReviewsViewModel,
                commentsViewModel,
                logMediaViewModel
        );

        cardPanel.add(
                mediaDetailView,
                mediaDetailView.getViewName()
        );

        return this;
    }

    /**
     * Adds the Media Detail Use Case to the application.
     *
     * @return the add media detail use case
     */
    public AppBuilder addMediaDetailUseCase() {
        final MediaDetailOutputBoundary mediaDetailPresenter =
                new MediaDetailPresenter(
                        viewManagerModel,
                        mediaDetailViewModel,
                        mediaReviewsViewModel
                );

        final MediaDetailInputBoundary mediaDetailInteractor =
                new MediaDetailInteractor(mediaDetailPresenter);

        final MediaDetailController mediaDetailController =
                new MediaDetailController(mediaDetailInteractor);

        searchResultView.setMediaDetailController(
                mediaDetailController
        );

        mediaDetailView.setMediaDetailController(
                mediaDetailController
        );
        mediaDetailView.setMediaReviewsController(
                createMediaReviewsController()
        );
        mediaDetailView.setCommentsController(
                createCommentsController()
        );
        // Without this the reviews panel never learns who is signed in, and
        // Write Review silently does nothing.
        mediaDetailView.setCurrentUserSource(
                userDataAccessObject::getCurrentUsername,
                userDataAccessObject::getDisplayName);

        mediaDetailView.setLogMediaController(
                createLogMediaController()
        );

        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     *
     * @return the build
     */
    public JFrame build() {
        final JFrame application = new JFrame(WINDOW_TITLE);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Padding and palette are applied here, once, rather than inside each
        // screen, so no view has to know about the theme.
        for (Component card : cardPanel.getComponents()) {
            if (card instanceof JComponent) {
                UiTheme.padScreen((JComponent) card);
            }
            UiTheme.applyTo(card);
            // Before the heading is styled, so the heading keeps its own size.
            UiTheme.applyDefaultTextSize(card);
            UiTheme.styleFirstLabelAsTitle((Container) card);
            if (card instanceof JPanel) {
                UiTheme.tidyVerticalScreen((JPanel) card);
            }
        }
        cardPanel.setBackground(UiTheme.BACKGROUND);

        // CardLayout stretches the active screen to fill, so putting the card
        // panel in the centre is what makes the app respond to a resize.
        application.setLayout(new BorderLayout());
        application.add(cardPanel, BorderLayout.CENTER);

        application.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        application.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        application.setLocationRelativeTo(null);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

    // HELPER
    private GetListsController createGetListsController() {
        final GetWatchHistoryOutputBoundary getWatchHistoryOutputBoundary = new GetListsPresenter(viewManagerModel,
                getListsViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetWatchHistoryInputBoundary getWatchHistoryInteractor = new GetWatchHistoryInteractor(
                userDataAccessObject, getWatchHistoryOutputBoundary);
        final GetWatchlistOutputBoundary getWatchlistOutputBoundary = new GetListsPresenter(viewManagerModel,
                getListsViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetWatchlistInputBoundary getWatchlistInteractor = new GetWatchlistInteractor(
                userDataAccessObject, getWatchlistOutputBoundary);
        final GetBlockedUsersOutputBoundary getBlockedUsersOutputBoundary = new GetListsPresenter(viewManagerModel,
                getListsViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetBlockedUsersInputBoundary getBlockedUsersInteractor = new GetBlockedUsersInteractor(
                userDataAccessObject, getBlockedUsersOutputBoundary);

        final GetListsController getListsController = new GetListsController(getWatchlistInteractor,
                getWatchHistoryInteractor, getBlockedUsersInteractor);
        return getListsController;
    }

    private MediaReviewsController createMediaReviewsController() {
        final MediaReviewsPresenter mediaReviewsPresenter =
                new MediaReviewsPresenter(mediaReviewsViewModel);
        final GetMediaReviewsInteractor getMediaReviewsInteractor =
                new GetMediaReviewsInteractor(mediaReviewDataAccessObject,
                        mediaReviewsPresenter);
        final CreateReviewInteractor createReviewInteractor =
                new CreateReviewInteractor(reviewDataAccessObject,
                        mediaReviewsPresenter);
        final EditReviewInteractor editReviewInteractor =
                new EditReviewInteractor(reviewDataAccessObject,
                        mediaReviewsPresenter);
        final DeleteReviewInteractor deleteReviewInteractor =
                new DeleteReviewInteractor(reviewDataAccessObject,
                        mediaReviewsPresenter);
        final LikeReviewInteractor likeReviewInteractor =
                new LikeReviewInteractor(reviewDataAccessObject,
                        mediaReviewsPresenter);
        final UnlikeReviewInteractor unlikeReviewInteractor =
                new UnlikeReviewInteractor(reviewDataAccessObject,
                        mediaReviewsPresenter);

        return new MediaReviewsController(getMediaReviewsInteractor,
                createReviewInteractor, editReviewInteractor,
                deleteReviewInteractor, likeReviewInteractor,
                unlikeReviewInteractor);
    }

    private CommentsController createCommentsController() {
        final CommentsPresenter commentsPresenter =
                new CommentsPresenter(commentsViewModel);
        final GetReviewCommentsInteractor getReviewCommentsInteractor =
                new GetReviewCommentsInteractor(commentDataAccessObject,
                        commentsPresenter);
        final CreateCommentInteractor createCommentInteractor =
                new CreateCommentInteractor(commentDataAccessObject,
                        commentsPresenter);
        final DeleteCommentInteractor deleteCommentInteractor =
                new DeleteCommentInteractor(commentDataAccessObject,
                        commentsPresenter);
        final LikeCommentInteractor likeCommentInteractor =
                new LikeCommentInteractor(commentDataAccessObject,
                        commentsPresenter);
        final UnlikeCommentInteractor unlikeCommentInteractor =
                new UnlikeCommentInteractor(commentDataAccessObject,
                        commentsPresenter);

        return new CommentsController(getReviewCommentsInteractor,
                createCommentInteractor, deleteCommentInteractor,
                likeCommentInteractor, unlikeCommentInteractor);
    }

    private LogMediaController createLogMediaController() {
        final LogMediaOutputBoundary logMediaPresenter =
                new LogMediaPresenter(logMediaViewModel);
        final LogMediaInputBoundary logMediaInteractor =
                new LogMediaInteractor(userDataAccessObject,
                        logMediaPresenter);

        return new LogMediaController(logMediaInteractor);
    }

}
