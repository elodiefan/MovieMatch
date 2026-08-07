package use_case.search_user;

import entity.User;
import use_case.search.Searchable;

/**
 * Interface for accessing user data for search.
 *
 * Mirrors SearchMediaDataAccess, which searches media the same way. Both
 * get their search method from Searchable, so a keyword means the
 * same thing whichever kind of thing is being searched.
 */
public interface SearchUserDataAccess
        extends Searchable<User> {

}
