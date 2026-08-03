package use_case.view_lists;

/**
 * The List View Interactor.
 */

public class ViewListsInteractor implements ViewListsInputBoundary {

    private final ViewListsUserDataAccessInterface userDataAccessObject;
    private final ViewListsOutputBoundary viewListsPresenter;

    public ViewListsInteractor(ViewListsUserDataAccessInterface userDataAccessInterface,
                               ViewListsOutputBoundary viewListsOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.viewListsPresenter = viewListsOutputBoundary;
    }

    /**
     * Switches from list view to account view.
     */
    @Override
    public void switchToAccountView() {
        viewListsPresenter.switchToAccountView();
    }
}
