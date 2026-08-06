package use_case.customize;

/**
 * The customize interactor.
 */
public class CustomizeInteractor implements CustomizeInputBoundary {
    private final CustomizeDataAccessInterface userDataAccessObject;
    private final CustomizeOutputBoundary customizePresenter;

    @Override
    public void executeColourChange(CustomizeInputData customizeInputData) {
        if !(customizeInputData.isCustomizable()) {
            userDataAccessObject.checkValidCode();
        }
    }

}
