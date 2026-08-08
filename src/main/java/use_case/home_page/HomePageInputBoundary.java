package use_case.home_page;

/**
 * Input Boundary for actions related to home page.
 */
public interface HomePageInputBoundary {

//    /**
//     * Executes the switch to search use case.
//     */
//    void switchToSearchView();

    /**
     * Executes the switch to personal account use case.
     * @param response the home page input data
     */
    void switchToPersonalAccountView(HomePageInputData response);
}
