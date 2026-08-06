package use_case.get_security_question;

/**
 * The Account Interactor.
 */
public class GetSecurityQuestionInteractor implements GetSecurityQuestionInputBoundary {
    private final GetSecurityQuestionUserDataAccessInterface userDataAccessObject;
    private final GetSecurityQuestionOutputBoundary personalAccountPresenter;

    public GetSecurityQuestionInteractor(GetSecurityQuestionUserDataAccessInterface userDataAccessInterface,
                                         GetSecurityQuestionOutputBoundary getSecurityQuestionOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.personalAccountPresenter = getSecurityQuestionOutputBoundary;
    }

    /**
     * Switches from account view to delete account view.
     */
    @Override
    public void switchToDeleteAccountView() {
        final String username = userDataAccessObject.getCurrentUsername();
        final String secuirtyQuestion = userDataAccessObject.getSecurityQuestion();
        final GetSecurityQuestionOutputData getSecurityQuestionOutputData = new
                GetSecurityQuestionOutputData(username, secuirtyQuestion);
        personalAccountPresenter.switchToDeleteAccountView(getSecurityQuestionOutputData);
    }

}

