package use_case.search_user;

/**
 * Input boundary for actions related to searching for users.
 */
public interface SearchUserInputBoundary {

    /**
     * Executes the search user use case.
     * @param searchUserInputData the keyword to search for
     */
    void execute(SearchUserInputData searchUserInputData);
}
