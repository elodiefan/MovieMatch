package use_case.get_lists;

/** Input Data for the Watchlist View Use Case. */
public class GetListsInputData {

    private final String username;
    private final String dislayName;

    public GetListsInputData(String username, String displayName) {
        this.username = username;
        this.dislayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getDislayName() {
        return dislayName;
    }
}
