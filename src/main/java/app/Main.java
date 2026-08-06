package app;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
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
                .addPersonalAccountUseCase()
                .addResetPasswordUseCase()
//                .addReviewsUseCase()
                .addSecurityQuestionUseCase()
                .addSignupUseCase()
                .addSearchView()
                .addSearchResultView()
                .addSearchUseCase()
                .addFilterUseCase()
                .addUserReviewsUseCase()
                .addMediaDetailView()
                .addMediaDetailUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
