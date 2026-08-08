package use_case.home_page;

/**
 * The Home Page Interactor.
 */
public class HomePageInteractor implements HomePageInputBoundary {
    private final HomePageUserDataAccessInterface userDataAccessObject;
    private final HomePageOutputBoundary userPresenter;

    public HomePageInteractor(HomePageUserDataAccessInterface homePageDataAccessInterface,
                              HomePageOutputBoundary homePageOutputBoundary) {
        this.userDataAccessObject = homePageDataAccessInterface;
        this.userPresenter = homePageOutputBoundary;
    }

//    @Override
//    public void switchToSearchView() {
//        userPresenter.switchToSearchView();
//    }

    @Override
    public void switchToPersonalAccountView(HomePageInputData response) {
        final String displayName = userDataAccessObject.getDisplayName();
        final HomePageOutputData homePageOutputData = new HomePageOutputData(response.getUsername(), displayName, false);
        userPresenter.switchToPersonalAccountView(homePageOutputData);
    }
}
