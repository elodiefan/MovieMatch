package app;

import javax.swing.JFrame;

import view.ErrorReporter;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        // Without this a failed database call just prints to the console and the
        // window silently does nothing, which looks identical to a dead button.
        ErrorReporter.install();

        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addDeleteAccountView()
                .addGetListsView()
                .addHomePageView()
                .addLoginView()
                .addLogoutView()
                .addOtherAccountView()
                .addPersonalAccountView()
                .addResetPasswordView()
                .addUserReviewsView()
                .addSearchUserView()
                .addSecurityQuestionView()
                .addSignupView()
                .addChangeDisplayNameView()
                .addChangeUsernameView()
                .addDeleteAccountUseCase()
                .addGetWatchlistUseCase()
                .addGetWatchHistoryUseCase()
                .addGetBlockedUsersUseCase()
                .addGetProfileUseCase()
//                .addHomePageUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addPersonalAccountUseCase()
                .addResetPasswordUseCase()
//                .addReviewsUseCase()
                .addSearchUserUseCase()
                .addSecurityQuestionUseCase()
                .addSignupUseCase()
                .addSearchView()
                .addSearchResultView()
                .addSearchUseCase()
                .addFilterUseCase()
                .addUserReviewsUseCase()
                .addMediaDetailView()
                .addMediaDetailUseCase()
                .addChangeDisplayNameUseCase()
                .addChangeUsernameUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
