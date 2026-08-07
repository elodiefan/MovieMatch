package use_case.search_user;

import java.util.List;

/** Output Data for the Search User Use Case. */
public class SearchUserOutputData {

    private final List<UserSummary> results;
    private final String keyword;

    public SearchUserOutputData(List<UserSummary> results, String keyword) {
        this.results = results;
        this.keyword = keyword;
    }

    /** Returns the users that matched. */
    public List<UserSummary> getResults() {
        return results;
    }

    /** Returns the keyword that was searched for, so the view can say what it found nothing for. */
    public String getKeyword() {
        return keyword;
    }
}
