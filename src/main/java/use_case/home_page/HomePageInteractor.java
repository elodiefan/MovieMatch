package use_case.home_page;

import entity.UserFactory;

/**
 * The Home Page Interactor.
 */
public class HomePageInteractor implements HomePageInputBoundary {
    private final HomePageUserDataAccessInterface userDataAccessObject;
    private final HomePageOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public HomePageInteractor(HomePageUserDataAccessInterface homePageDataAccessInterface,
                              HomePageOutputBoundary homePageOutputBoundary, UserFactory userFactory) {
        this.userDataAccessObject = homePageDataAccessInterface;
        this.userPresenter = homePageOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void switchToSearchView() {
        userPresenter.switchToSearchView();
    }

    @Override
    public void switchToAccountView(HomePageInputData response) {
        final HomePageOutputData homePageOutputData = new HomePageOutputData(response.getUsername(), false);
        userPresenter.switchToAccountView(homePageOutputData);
    }
}
