package use_case.recommendation;

import java.util.Set;

/** What the recommendation use case needs to know about a user's own lists. */
public interface WatchedMediaDataAccessInterface {

    /** Returns the ids of every title the user has put on their watchlist or watch history. */
    Set<Integer> findEngagedMediaIds(String username);
}
