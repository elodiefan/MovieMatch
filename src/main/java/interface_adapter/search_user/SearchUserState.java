package interface_adapter.search_user;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Search User View Model.
 */
public class SearchUserState {

    private List<UserSearchRow> results = new ArrayList<>();
    private String keyword = "";
    private String searchError;
    private boolean searched;

    public List<UserSearchRow> getResults() {
        return results;
    }

    public void setResults(List<UserSearchRow> results) {
        this.results = results;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearchError() {
        return searchError;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    /**
     * Whether a search has run yet. Lets the view tell "nobody matched" apart
     * from "you have not searched for anything yet", which both show an empty
     * list but mean different things.
     * @return true once a search has completed
     */
    public boolean isSearched() {
        return searched;
    }

    public void setSearched(boolean searched) {
        this.searched = searched;
    }
}
