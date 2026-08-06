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
//                .addReviewsView()
                .addSecurityQuestionView()
                .addSignupView()
                .addDeleteAccountUseCase()
                .addGetWatchlistUseCase()
                .addGetWatchHistoryUseCase()
                .addGetBlockedUsersUseCase()
                .addGetProfileUseCase()
//                .addHomePageUseCase()
                .addLoginUseCase()
                .addPersonalAccountUseCase()
                .addResetPasswordUseCase()
//                .addReviewsUseCase()
                .addSecurityQuestionUseCase()
                .addSignupUseCase()
                .addSearchView()
                .addSearchResultView()
                .addSearchUseCase()
                .addFilterUseCase()
                .addMediaDetailView()
                .addMediaDetailUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
