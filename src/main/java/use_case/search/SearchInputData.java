package use_case.search;

/** The Input Data for the Search Use Case. */
public class SearchInputData {

    private final String keyword;
    private final int startPage;

    public SearchInputData(String keyword) {
        this(keyword, 1);
    }

    public SearchInputData(String keyword, int startPage) {
        this.keyword = keyword;
        this.startPage = startPage;
    }

    /** Returns the search keyword. */
    public String getKeyword() {
        return keyword;
    }

    /** Returns the first page this request should fetch. */
    public int getStartPage() {
        return startPage;
    }
}
