package use_case.search_user;

/**
 * The Input Data for the Search User Use Case.
 */
public class SearchUserInputData {

    private final String keyword;

    public SearchUserInputData(String keyword) {
        this.keyword = keyword;
    }

    String getKeyword() {
        return keyword;
    }
}
