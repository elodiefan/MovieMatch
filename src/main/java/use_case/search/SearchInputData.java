package use_case.search;

/**
 * The Input Data for the Search Use Case.
 */
public class SearchInputData {

    private final String keyword;

    public SearchInputData(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the search keyword.
     */
    public String getKeyword() {
        return keyword;
    }
}
