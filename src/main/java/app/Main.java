package app;

import javax.swing.JFrame;

import view.ErrorReporter;
import view.UiTheme;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     */
    public static void main(String[] args) {
        // Without this a failed database call just prints to the console and the
        // window silently does nothing, which looks identical to a dead button.
        ErrorReporter.install();

        // Swing bakes fonts and colours in at construction time, so the theme
        // has to be installed before the first view is created.
        UiTheme.install();

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
                .addDeleteAccountUseCase()
                .addGetWatchlistUseCase()
                .addGetWatchHistoryUseCase()
                .addGetBlockedUsersUseCase()
                .addGetProfileUseCase()
//                .addHomePageUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addOtherAccountUseCase()
                .addPersonalAccountUseCase()
                .addResetPasswordUseCase()
//                .addReviewsUseCase()
                .addSearchUserUseCase()
                .addSecurityQuestionUseCase()
                .addSignupUseCase()
                .addSearchView()
                .addSearchResultView()
                .addSettingsView()
                .addSearchUseCase()
                .addSettingsUseCase()
                .addFilterUseCase()
                .addUserReviewsUseCase()
                .addMediaDetailView()
                .addMediaDetailUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
