package use_case.view_lists;

import java.util.List;

/**
 * DAO for the Account Use Case.
 */
public interface ViewListsUserDataAccessInterface {

    /**
     * Returns 
     * @return
     */
    List<Integer> getWatchlist();

    List<Integer> getWatchHistory();

    List<Integer> getReviews();

    List<String> getBlockedUsers();
}
