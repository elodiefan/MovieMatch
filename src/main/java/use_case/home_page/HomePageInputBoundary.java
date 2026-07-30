package use_case.home_page;

/**
 * Input Boundary for actions related to home page.
 */
public interface HomePageInputBoundary {

    /**
     * Executes the home page use case.
     * @param homePageInputData the input data
     */
    void execute(HomePageInputData homePageInputData);
}
