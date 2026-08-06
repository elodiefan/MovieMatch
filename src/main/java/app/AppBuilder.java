package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryLockoutTracker;
import data_access.InMemoryReviewDataAccessObject;
import data_access.MongoCommentDataAccessObject;
import data_access.MongoReviewDataAccessObject;
import data_access.MongoUserDataAccessObject;
import data_access.UserDataAccessObject;

import interface_adapter.user_reviews.UserReviewsController;
import interface_adapter.user_reviews.UserReviewsViewModel;
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
import interface_adapter.media_detail.MediaDetailController;
import interface_adapter.media_detail.MediaDetailPresenter;
import interface_adapter.media_detail.MediaDetailViewModel;
import interface_adapter.media_reviews.MediaReviewsPresenter;
import interface_adapter.media_reviews.MediaReviewsViewModel;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountController;
import interface_adapter.personal_account.PersonalAccountPresenter;
import interface_adapter.personal_account.PersonalAccountViewModel;
import interface_adapter.reset_password.ResetPasswordController;
import interface_adapter.reset_password.ResetPasswordPresenter;
import interface_adapter.reset_password.ResetPasswordViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search_result.SearchResultViewModel;
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
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.get_lists.get_blocked_users.GetBlockedUsersInputBoundary;
import use_case.get_lists.get_blocked_users.GetBlockedUsersInteractor;
import use_case.get_lists.get_blocked_users.GetBlockedUsersOutputBoundary;
import use_case.get_lists.get_watch_history.GetWatchHistoryInputBoundary;
import use_case.get_lists.get_watch_history.GetWatchHistoryInteractor;
import use_case.get_lists.get_watch_history.GetWatchHistoryOutputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistOutputBoundary;
import use_case.get_profile.GetProfileInputBoundary;
import use_case.get_profile.GetProfileInteractor;
import use_case.get_profile.GetProfileOutputBoundary;
import use_case.delete_account.DeleteAccountInputBoundary;
import use_case.delete_account.DeleteAccountInteractor;
import use_case.delete_account.DeleteAccountOutputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInteractor;
import use_case.get_security_question.GetSecurityQuestionInputBoundary;
import use_case.get_security_question.GetSecurityQuestionInteractor;
import use_case.get_security_question.GetSecurityQuestionOutputBoundary;
import use_case.comment.get_user_comments.GetUserCommentsInteractor;
import use_case.media_detail.MediaDetailInputBoundary;
import use_case.media_detail.MediaDetailInteractor;
import use_case.media_detail.MediaDetailOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.reset_password.ResetPasswordInputBoundary;
import use_case.reset_password.ResetPasswordInteractor;
import use_case.reset_password.ResetPasswordOutputBoundary;
import use_case.review.delete_review.DeleteReviewInteractor;
import use_case.review.edit_review.EditReviewInteractor;
import use_case.review.get_user_reviews.GetUserReviewsInteractor;
import use_case.review.like_review.LikeReviewInteractor;
import use_case.review.unlike_review.UnlikeReviewInteractor;
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
    private final InMemoryReviewDataAccessObject reviewDataAccessObject =
            new InMemoryReviewDataAccessObject();
    private final MongoReviewDataAccessObject mongoReviewDataAccessObject =
            new MongoReviewDataAccessObject();
    private final MongoCommentDataAccessObject mongoCommentDataAccessObject =
            new MongoCommentDataAccessObject();

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
    private MyReviewsView myReviewsView;
    private UserReviewsViewModel userReviewsViewModel;
    private ResetPasswordView resetPasswordView;
    private ResetPasswordViewModel resetPasswordViewModel;
//    private ReviewsView reviewsView;
//    private ReviewsViewModel reviewsViewModel;
    private SecurityQuestionView securityQuestionView;
    private SecurityQuestionViewModel securityQuestionViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private SearchResultView searchResultView;
    private SearchResultViewModel searchResultViewModel;
    private MediaDetailView mediaDetailView;
    private MediaDetailViewModel mediaDetailViewModel;
    private MediaReviewsViewModel mediaReviewsViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
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
     * Adds the Get Lists View to the application.
     * @return this builder
     */
    public AppBuilder addGetListsView() {
        getListsViewModel = new GetListsViewModel();
        getListsView = new GetListsView(getListsViewModel);
        cardPanel.add(getListsView, getListsView.getViewName());
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
     * Adds the Other Account View to the application.
     * @return this builder
     */
    public AppBuilder addOtherAccountView() {
        otherAccountViewModel = new OtherAccountViewModel();
        otherAccountView = new OtherAccountView(otherAccountViewModel);
        cardPanel.add(otherAccountView, otherAccountView.getViewName());
        return this;
    }

    /**
     * Adds the Personal Account View to the application.
     * @return this builder
     */
    public AppBuilder addPersonalAccountView() {
        personalAccountViewModel = new PersonalAccountViewModel();
        personalAccountView = new PersonalAccountView(personalAccountViewModel);
        cardPanel.add(personalAccountView, personalAccountView.getViewName());
        return this;
    }

    /**
     * Adds the My Reviews View to the application.
     * @return this builder
     */
    public AppBuilder addMyReviewsView() {
        userReviewsViewModel = new UserReviewsViewModel();
        myReviewsView = new MyReviewsView(userReviewsViewModel);
        myReviewsView.getBackButton().addActionListener(
                event -> viewManagerModel.switchView(
                        personalAccountViewModel.getViewName()));
        cardPanel.add(myReviewsView, myReviewsView.getViewName());
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

//    /**
//     * Adds the Reviews View to the application.
//     * @return this builder
//     */
//    public AppBuilder addReviewsView() {
//        reviewsViewModel = new ReviewsViewModel();
//        reviewsView = new ReviewsView(reviewsViewModel);
//        cardPanel.add(reviewsView, reviewsView.getViewName());
//        return this;
//    }

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
     * Adds the Delete Account Use Case to the application.
     * @return this builder
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
     * @return this builder
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
     * @return this builder
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
     * @return this builder
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
     * @return this builder
     */
    public AppBuilder addGetProfileUseCase() {
        final GetProfileOutputBoundary userPresenter = new HomePagePresenter(viewManagerModel,
                homePageViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetProfileInputBoundary getProfileInteractor = new GetProfileInteractor(userDataAccessObject,
                (HomePagePresenter) userPresenter);

        final HomePageController homePageController =
                new HomePageController(
                        getProfileInteractor,
                        viewManagerModel
                );
        homePageView.setHomePageController(homePageController);
        return this;
//        final HomePageOutputBoundary homePageOutputBoundary = new HomePagePresenter(viewManagerModel,
//               homePageViewModel, searchViewModel, accountViewModel);
//        final HomePageOutputBoundary homePageOutputBoundary = new HomePagePresenter(viewManagerModel,
//                homePageViewModel, accountViewModel);
//        final HomePageInputBoundary homePageInteractor = new HomePageInteractor(
//                userDataAccessObject, homePageOutputBoundary, userFactory);
//
//        final HomePageController homePageController = new HomePageController(homePageInteractor);
//        homePageView.setHomePageController(homePageController);
//        return this;
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

    /**
     * Adds the Personal Account Use Case to the application.
     * @return this builder
     */
    public AppBuilder addPersonalAccountUseCase() {
        final GetProfileOutputBoundary getProfileOutputBoundary = new HomePagePresenter(viewManagerModel,
                homePageViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetProfileInputBoundary getProfileInteractor = new GetProfileInteractor(userDataAccessObject,
                (HomePagePresenter) getProfileOutputBoundary);

        final GetListsController getListsController = createGetListsController();
        final GetSecurityQuestionOutputBoundary getSecurityQuestionOutputBoundary = new PersonalAccountPresenter(viewManagerModel,
                personalAccountViewModel, resetPasswordViewModel, deleteAccountViewModel);
        final GetSecurityQuestionInputBoundary getSecurityQuestionInteractor = new GetSecurityQuestionInteractor(userDataAccessObject,
                getSecurityQuestionOutputBoundary);

        final PersonalAccountController personalAccountController = new PersonalAccountController(viewManagerModel,
                getSecurityQuestionInteractor,
                getListsController,
                resetPasswordViewModel.getViewName(),
                homePageViewModel.getViewName(),
                getListsViewModel.getViewName());

        personalAccountView.setPersonalAccountController(personalAccountController);
        return this;
    }

    /**
     * Adds the My Reviews use case wiring to the application.
     * @return this builder
     */
    public AppBuilder addUserReviewsUseCase() {
        myReviewsView.setUserReviewsController(createUserReviewsController());
        return this;
    }

//
//    /**
//     * Adds the Other Account Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addGetProfileUseCase() {
//        final GetProfileOutputBoundary getProfileOutputBoundary = new HomePagePresenter();
//        final GetProfileInputBoundary getProfileInteractor = new GetProfileInteractor(userDataAccessObject,
//                getProfileOutputBoundary);
//        // viewManagerModel, accountViewModel, resetPasswordViewModel, deleteAccountViewModel);
////        final AccountInputBoundary accountInteractor = new AccountInteractor(
////                userDataAccessObject, accountOutputBoundary);
//
//        final AccountController accountController = new AccountController(accountInteractor);
//        accountView.setAccountController(accountController);
//        return this;
//    }

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
     * is saved" — the presenter calls it so it does not need to know which screen
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
     * Adds the Search View to the application.
     *
     * @return this builder
     */
    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);

        cardPanel.add(
                searchView,
                searchView.getViewName()
        );

        return this;
    }

    /**
     * Adds the Search Result View to the application.
     *
     * @return this builder
     */
    public AppBuilder addSearchResultView() {
        searchResultViewModel = new SearchResultViewModel();
        searchResultView = new SearchResultView(searchResultViewModel);
        cardPanel.add(searchResultView, searchResultView.getViewName());
        return this;
    }

    /**
     * Adds the Search Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addSearchUseCase() {
        SearchUseCaseFactory.create(
                viewManagerModel,
                searchViewModel,
                searchResultViewModel,
                searchView
        );
        return this;
    }

    /**
     * Adds the Filter Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addFilterUseCase() {
        final FilterOutputBoundary filterPresenter =
                new FilterPresenter(searchResultViewModel);

        final FilterInputBoundary filterInteractor =
                new FilterInteractor(filterPresenter);

        final FilterController filterController =
                new FilterController(
                        filterInteractor,
                        searchResultViewModel
                );

        searchResultView.setFilterController(filterController);

        return this;
    }

    /**
     * Adds the Media Detail View to the application.
     *
     * @return this builder
     */
    public AppBuilder addMediaDetailView() {
        mediaDetailViewModel = new MediaDetailViewModel();
        mediaReviewsViewModel = new MediaReviewsViewModel();

        mediaDetailView = new MediaDetailView(
                mediaDetailViewModel,
                mediaReviewsViewModel
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
     * @return this builder
     */
    public AppBuilder addMediaDetailUseCase() {
        final MediaReviewsPresenter mediaReviewsPresenter =
                new MediaReviewsPresenter();

        final MediaDetailOutputBoundary mediaDetailPresenter =
                new MediaDetailPresenter(
                        viewManagerModel,
                        mediaDetailViewModel,
                        mediaReviewsViewModel,
                        mediaReviewsPresenter,
                        reviewDataAccessObject
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

    private UserReviewsController createUserReviewsController() {
        final GetUserReviewsInteractor getUserReviewsInteractor =
                new GetUserReviewsInteractor(mongoReviewDataAccessObject);
        final EditReviewInteractor editReviewInteractor =
                new EditReviewInteractor(mongoReviewDataAccessObject);
        final DeleteReviewInteractor deleteReviewInteractor =
                new DeleteReviewInteractor(mongoReviewDataAccessObject);
        final LikeReviewInteractor likeReviewInteractor =
                new LikeReviewInteractor(mongoReviewDataAccessObject);
        final UnlikeReviewInteractor unlikeReviewInteractor =
                new UnlikeReviewInteractor(mongoReviewDataAccessObject);
        final GetUserCommentsInteractor getUserCommentsInteractor =
                new GetUserCommentsInteractor(mongoCommentDataAccessObject,
                        mongoReviewDataAccessObject);

        return new UserReviewsController(getUserReviewsInteractor,
                editReviewInteractor, deleteReviewInteractor,
                likeReviewInteractor, unlikeReviewInteractor,
                getUserCommentsInteractor);
    }
}
