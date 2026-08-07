package use_case.search_user;

import entity.User;
import use_case.search.Searchable;

/** Interface for accessing user data for search. */
public interface SearchUserDataAccess
        extends Searchable<User> {

}
