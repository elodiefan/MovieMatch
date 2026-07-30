package use_case.home_page;

/**
 * The output boundary for the Home Page Use Case.
 */

public interface HomePageOutputBoundary {
    /**
     * Prepares the search success view for the Home Page Use Case.
     * @param outputData the output data
     */
    void prepareSearchSuccessView(HomePageOutputData outputData);

    /**
     * Prepares the account success view for the Home Page Use Case.
     * @param outputData the output data
     */
    void prepareAccountSuccessView(HomePageOutputData outputData);
}
