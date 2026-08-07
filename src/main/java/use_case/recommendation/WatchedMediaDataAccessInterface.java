package use_case.recommendation;

import java.util.Set;

/**
 * What the recommendation use case needs to know about a user's own lists.
 * <p>
 * Declared here rather than in data access so the interactor depends on the
 * idea of "titles this person has engaged with", not on how MovieMatch happens
 * to store watchlists today.
 * <p>
 * Kept apart from {@link RecommendationDataAccessInterface}, which is about
 * ratings and friends, because this is answered by the user's own record while
 * that will eventually be answered by the review store.
 */
public interface WatchedMediaDataAccessInterface {

    /**
     * Returns the ids of every title the user has put on their watchlist or
     * watch history.
     * <p>
     * Both count. A title already watched should not be recommended again, and
     * one already on the watchlist has been chosen, so neither belongs in a set
     * of suggestions. Both also say something about what the person likes.
     */
    Set<Integer> findEngagedMediaIds(String username);
}
