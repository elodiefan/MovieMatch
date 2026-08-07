package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.*;

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
import interface_adapter.messaging.MessagingController;
import interface_adapter.messaging.MessagingPresenter;
import interface_adapter.messaging.MessagingViewModel;
import interface_adapter.other_account.OtherAccountController;
import interface_adapter.other_account.OtherAccountPresenter;
import interface_adapter.other_account.OtherAccountViewModel;
import interface_adapter.personal_account.PersonalAccountController;
import interface_adapter.personal_account.PersonalAccountPresenter;
import interface_adapter.personal_account.PersonalAccountViewModel;
import interface_adapter.reset_password.ResetPasswordController;
import interface_adapter.reset_password.ResetPasswordPresenter;
import interface_adapter.reset_password.ResetPasswordViewModel;
import use_case.access_message_chat.AccessMessageChatInputBoundary;
import use_case.access_message_chat.AccessMessageChatInteractor;
import use_case.access_message_chat.AccessMessageChatOutputBoundary;
import use_case.block_user.BlockUserInputBoundary;
import use_case.block_user.BlockUserInteractor;
import use_case.block_user.BlockUserOutputBoundary;
import use_case.fetch_chat_history.FetchChatHistoryInputBoundary;
import use_case.fetch_chat_history.FetchChatHistoryInteractor;
import use_case.fetch_chat_history.FetchChatHistoryOutputBoundary;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInteractor;
import use_case.send_message.SendMessageOutputBoundary;
import view.*;
import interface_adapter.search_user.SearchUserViewModel;
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
import use_case.comment.create_comment.CreateCommentInteractor;
import use_case.comment.delete_comment.DeleteCommentInteractor;
import use_case.comment.get_review_comments.GetReviewCommentsInteractor;
import use_case.comment.get_user_comments.GetUserCommentsInputBoundary;
import use_case.comment.get_user_comments.GetUserCommentsInteractor;
import use_case.comment.like_comment.LikeCommentInteractor;
import use_case.comment.unlike_comment.UnlikeCommentInteractor;
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
import use_case.comment.unlike_comment.delete_account.DeleteAccountInputBoundary;
import use_case.comment.unlike_comment.delete_account.DeleteAccountInteractor;
import use_case.comment.unlike_comment.delete_account.DeleteAccountOutputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInputBoundary;
import use_case.get_lists.get_watchlist.GetWatchlistInteractor;
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
import use_case.review.create_review.CreateReviewInteractor;
import use_case.review.delete_review.DeleteReviewInputBoundary;
import use_case.review.delete_review.DeleteReviewInteractor;
import use_case.review.edit_review.EditReviewInputBoundary;
import use_case.review.edit_review.EditReviewInteractor;
import use_case.review.get_media_reviews.GetMediaReviewsInteractor;
import use_case.review.get_user_reviews.GetUserReviewsInputBoundary;
import use_case.review.get_user_reviews.GetUserReviewsInteractor;
import use_case.review.get_user_reviews.GetUserReviewsOutputBoundary;
import use_case.review.like_review.LikeReviewInputBoundary;
import use_case.review.like_review.LikeReviewInteractor;
import use_case.review.unlike_review.UnlikeReviewInputBoundary;
import use_case.review.unlike_review.UnlikeReviewInteractor;
import use_case.security_question.SecurityQuestionInputBoundary;
import use_case.security_question.SecurityQuestionInteractor;
import use_case.security_question.SecurityQuestionOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;

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
    private final ReviewDataAccessObject reviewDataAccessObject = new MongoReviewDataAccessObject();
    private final CommentDataAccessObject commentDataAccessObject = new MongoCommentDataAccessObject();
    private final MongoMessagesDataAccessObject mongoMessagesDataAccessObject = new MongoMessagesDataAccessObject();

    // Counts failed security answers and holds lock-outs. One shared instance, so
    // every attempt on the same account is counted together.
    private final InMemoryLockoutTracker lockoutTracker = new InMemoryLockoutTracker();
    private final MongoReviewDataAccessObject mongoReviewDataAccessObject =
            new MongoReviewDataAccessObject();
    private final MongoCommentDataAccessObject mongoCommentDataAccessObject =
            new MongoCommentDataAccessObject();
    private final TmdbReviewDataAccessObject tmdbReviewDataAccessObject =
            new TmdbReviewDataAccessObject(new TmdbApiClient());
    private final CombinedMediaReviewDataAccessObject
            mediaReviewDataAccessObject =
            new CombinedMediaReviewDataAccessObject(tmdbReviewDataAccessObject,
                    mongoReviewDataAccessObject);

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
    private MediaDetailView mediaDetailView;
    private MediaDetailViewModel mediaDetailViewModel;
    private MediaReviewsViewModel mediaReviewsViewModel;
    private CommentsViewModel commentsViewModel;
    private LogMediaViewModel logMediaViewModel;
    private MessagingView messagingView;
    private MessagingViewModel messagingViewModel;

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
        logoutView = new LogoutConfirmView(logoutViewModel, viewManagerModel,
                PersonalAccountViewModel.VIEW_NAME);
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
     * Adds the My Reviews View to the application.
     * @return this builder
     */
    public AppBuilder addUserReviewsView() {
        userReviewsViewModel = new UserReviewsViewModel();
        userReviewsView = new MyReviewsView(userReviewsViewModel);
        cardPanel.add(userReviewsView, userReviewsView.getViewName());
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
     * Adds the Search User View to the application.
     * @return this builder
     */
    public AppBuilder addSearchUserView() {
        searchUserViewModel = new SearchUserViewModel();
        searchUserView = new SearchUserView(searchUserViewModel);
        cardPanel.add(searchUserView, searchUserView.getViewName());
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
     * Adds the Search User Use Case to the application.
     * <p>
     * The assembly lives in {@link SearchUserUseCaseFactory}, following the
     * convention Yidan set with {@code SearchUseCaseFactory}.
     * @return this builder
     */
    public AppBuilder addSearchUserUseCase() {
        final GetProfileOutputBoundary getProfileOutputBoundary = new HomePagePresenter(viewManagerModel,
                homePageViewModel, personalAccountViewModel, otherAccountViewModel);
        final GetProfileInputBoundary getProfileInteractor = new GetProfileInteractor(userDataAccessObject,
                (HomePagePresenter) getProfileOutputBoundary);

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
                logoutViewModel,
                resetPasswordViewModel.getViewName(),
                homePageViewModel.getViewName(),
                getListsViewModel.getViewName(),
                userReviewsViewModel.getViewName());

        personalAccountView.setPersonalAccountController(personalAccountController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * <p>
     * Every piece of this use case already existed but was never assembled, so the
     * Log Out button on the personal account page did nothing.
     * @return this builder
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

    /**
     * Adds the User Reviews Use Case to the application.
     * @return this builder
     */
    public AppBuilder addUserReviewsUseCase() {
        final GetUserReviewsOutputBoundary userReviewsOutputBoundary = new UserReviewsPresenter(userReviewsViewModel);
        final GetUserReviewsInputBoundary userReviewsInteractor = new GetUserReviewsInteractor(reviewDataAccessObject,
                userReviewsOutputBoundary);

        final EditReviewInputBoundary editReviewsInteractor = new EditReviewInteractor();
        final DeleteReviewInputBoundary deleteReviewsInteractor = new DeleteReviewInteractor();
        final LikeReviewInputBoundary likeReviewsInteractor = new LikeReviewInteractor();
        final UnlikeReviewInputBoundary unlikeReviewsInteractor = new UnlikeReviewInteractor();
        final GetUserCommentsInputBoundary userCommentsInteractor = new GetUserCommentsInteractor(commentDataAccessObject,
                reviewDataAccessObject);

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
        searchView = new SearchView(searchViewModel, viewManagerModel, HomePageViewModel.VIEW_NAME);

        cardPanel.add(
                searchView,
                searchView.getViewName()
        );

        return this;
    }

    /**
     * Adds the Messaging View to the app.
     * @return this builder
     */
    public AppBuilder addMessagingView() {
        messagingViewModel = new MessagingViewModel();
        messagingView = new MessagingView(messagingViewModel, viewManagerModel);

        cardPanel.add(
                messagingView,
                messagingView.getViewName()
        );

        return this;
    }

    /**
     * Adds access message chat use case to this app.
     * @return this builder
     */
    public AppBuilder addAccessMessageChatUseCase() {
        final AccessMessageChatOutputBoundary userPresenter = new OtherAccountPresenter(viewManagerModel,
                otherAccountViewModel, messagingViewModel);
        final AccessMessageChatInputBoundary accessMessageChatInteractor = new AccessMessageChatInteractor(
                userDataAccessObject, mongoMessagesDataAccessObject, userPresenter);
        final BlockUserInputBoundary blockUserInteractor = new BlockUserInteractor(userDataAccessObject,
                (BlockUserOutputBoundary) userPresenter);
        final OtherAccountController otherAccountController = new OtherAccountController(viewManagerModel,
                blockUserInteractor, createGetListsController(), accessMessageChatInteractor);
        otherAccountView.setOtherAccountController(otherAccountController);
        return this;
    }

    public AppBuilder addBlockUserUseCase() {
        final BlockUserOutputBoundary userPresenter = new OtherAccountPresenter(viewManagerModel,
                otherAccountViewModel, messagingViewModel);
        final BlockUserInputBoundary blockUserInteractor = new BlockUserInteractor(userDataAccessObject, userPresenter);
        final AccessMessageChatInputBoundary accessMessageChatInteractor = new AccessMessageChatInteractor(
                userDataAccessObject, mongoMessagesDataAccessObject, (AccessMessageChatOutputBoundary) userPresenter);
        final OtherAccountController otherAccountController = new OtherAccountController(viewManagerModel, blockUserInteractor, createGetListsController(), accessMessageChatInteractor);
        otherAccountView.setOtherAccountController(otherAccountController);
        return this;
    }

    /**
     * Adds use cases associated with messaging to this app.
     * @return this builder
     */
    public AppBuilder addMessagingUseCases() {
        final FetchChatHistoryOutputBoundary userPresenter = new MessagingPresenter(viewManagerModel,
                messagingViewModel, otherAccountViewModel);
        final FetchChatHistoryInputBoundary fetchChatHistoryInteractor = new FetchChatHistoryInteractor(
                mongoMessagesDataAccessObject, userPresenter);
        final SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(mongoMessagesDataAccessObject,
                (SendMessageOutputBoundary)  userPresenter);
        final MessagingController messagingController = new MessagingController(viewManagerModel, sendMessageInteractor,
                fetchChatHistoryInteractor, otherAccountView.getViewName());
        messagingView.setMessagingController(messagingController);
        return this;
    }

    /**
     * Adds the Search Result View to the application.
     *
     * @return this builder
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
     * @return this builder
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
        mediaDetailView.setLogMediaController(
                createLogMediaController()
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

    private MediaReviewsController createMediaReviewsController() {
        final MediaReviewsPresenter mediaReviewsPresenter =
                new MediaReviewsPresenter(mediaReviewsViewModel);
        final GetMediaReviewsInteractor getMediaReviewsInteractor =
                new GetMediaReviewsInteractor(mediaReviewDataAccessObject,
                        mediaReviewsPresenter);
        final CreateReviewInteractor createReviewInteractor =
                new CreateReviewInteractor(mongoReviewDataAccessObject,
                        mediaReviewsPresenter);
        final EditReviewInteractor editReviewInteractor =
                new EditReviewInteractor(mongoReviewDataAccessObject,
                        mediaReviewsPresenter);
        final DeleteReviewInteractor deleteReviewInteractor =
                new DeleteReviewInteractor(mongoReviewDataAccessObject,
                        mediaReviewsPresenter);
        final LikeReviewInteractor likeReviewInteractor =
                new LikeReviewInteractor(mongoReviewDataAccessObject,
                        mediaReviewsPresenter);
        final UnlikeReviewInteractor unlikeReviewInteractor =
                new UnlikeReviewInteractor(mongoReviewDataAccessObject,
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
                new GetReviewCommentsInteractor(mongoCommentDataAccessObject,
                        commentsPresenter);
        final CreateCommentInteractor createCommentInteractor =
                new CreateCommentInteractor(mongoCommentDataAccessObject,
                        commentsPresenter);
        final DeleteCommentInteractor deleteCommentInteractor =
                new DeleteCommentInteractor(mongoCommentDataAccessObject,
                        commentsPresenter);
        final LikeCommentInteractor likeCommentInteractor =
                new LikeCommentInteractor(mongoCommentDataAccessObject,
                        commentsPresenter);
        final UnlikeCommentInteractor unlikeCommentInteractor =
                new UnlikeCommentInteractor(mongoCommentDataAccessObject,
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
