package use_case.home_page;

import entity.User;
import entity.UserFactory;

/**
 * The Home Page Interactor.
 */
public class HomePageInteractor implements HomePageInputBoundary {
    private final HomePageDataAccessInterface userDataAccessObject;
    private final HomePageOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public HomePageInteractor(HomePageUserDataAccessInterface homePageDataAccessInterface,
                              HomePageOutputBoundary homePageOutputBoundary, UserFactory userFactory) {
        this.userDataAccessObject = homePageDataAccessInterface;
        this.userPresenter = homePageOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(HomePageInputData homePageInputData) {
        if (homePageInputData.)
    }
}
